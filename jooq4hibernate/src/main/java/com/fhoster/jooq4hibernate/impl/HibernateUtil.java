package com.fhoster.jooq4hibernate.impl;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.hibernate.type.CollectionType;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

class HibernateUtil {

    private final static Logger logger = LoggerFactory.getLogger(HibernateUtil.class);
    private final static String DOT                     = ".";

    private final SessionFactory sessionFactory;
    
    HibernateUtil(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
    
    protected static HibernateUtil using(SessionFactory sessionFactory){
    	return new HibernateUtil(sessionFactory);
    }

	protected Set<HibernateRelation> findHibernateRelationship(
        Set<SQLTable> tables,
        Map<SQLTable, Set<SQLJoinCondition>> joinConditions)
    {
    	logger.debug("JoinConditionMap: "+joinConditions);
    	
        Set<HibernateRelation> hibernateRelations = new LinkedHashSet<HibernateRelation>();
        Set<String> aliasList = new LinkedHashSet<String>();

        SQLTable rootTable = Iterables.get(tables, 0);

        AbstractEntityPersister entityRoot = getEntityClass(rootTable.getSchema(), rootTable.getName());
        aliasList.add(rootTable.getAlias());

        HibernateRelation rootHibernateRelation = new HibernateRelation(rootTable.getAlias(), rootTable.getAlias(), entityRoot.getMappedClass());
        hibernateRelations.add(rootHibernateRelation);

        for(SQLTable table : joinConditions.keySet()){
			for (SQLJoinCondition join : joinConditions.get(table)) {
				SQLTable leftTable = join.getLeftTable();
	            SQLTable rightTable = join.getRightTable();

	            logger.debug("Analyze join between " + leftTable + " and " + rightTable);

	            SQLTable primary = null;
	            SQLTable secondary = null;
	            String joinColumn = null;

	            if (aliasList.contains(leftTable.getAlias())) {
	                primary = leftTable;
	                secondary = rightTable;
	                joinColumn = join.getLeftColumn();
	            }
	            else if (aliasList.contains(rightTable.getAlias())) {
	                primary = rightTable;
	                secondary = leftTable;
	                joinColumn = join.getRightColumn();
	            }

	            if (primary == null || secondary == null) {
	                logger.warn("No relationship found for the JoinCondition " + join);
	            }

	            String primaryAlias = primary.getAlias();
	            String secondaryAlias = secondary.getAlias();
	            AbstractEntityPersister primaryEntity = getEntityClass(primary.getSchema(), primary.getName());
	            AbstractEntityPersister secondaryEntity = getEntityClass(secondary.getSchema(), secondary.getName());

	            //FIXME: instead of taking consideration of joinConditions alone, take care of the group of join conditions related to a SQLTable
	            Set<String> relations = findRelationProperties(primaryEntity, secondaryEntity, joinColumn);

	            if (relations.isEmpty()) {
	                logger.warn("Join Condition found but not mapped in HibernateRelation: " + join);
	            }

	            for (String relationProperty : relations) {
	                String relationPath = Joiner.on(DOT).join(primaryAlias, relationProperty);
	                HibernateRelation hibernateRelation = new HibernateRelation(secondaryAlias, relationPath, secondaryEntity.getMappedClass());
	                hibernateRelations.add(hibernateRelation);
	            }
			}
			
			aliasList.add(table.getAlias());
		}

        logger.debug("HibernateRelations found: " + hibernateRelations);

        return hibernateRelations;
    }

    protected Set<String> findRelationProperties(
        final AbstractEntityPersister primaryEntity,
        final AbstractEntityPersister secondaryEntityToResearch,
        final String primaryJoinColumn)
    {
        Set<String> relations = new HashSet<String>();

        String tableNameToResearch = secondaryEntityToResearch.getTableName();

        String[] propertyNames = primaryEntity.getPropertyNames();
        for (String propertyName : propertyNames) {
            Class<?> cls = getPropertyClass(primaryEntity, propertyName);

            if (existsEntityClass(cls)) {
                AbstractEntityPersister persister = getEntityClass(cls);
                String tableName = persister.getTableName();
                if (tableName.equals(tableNameToResearch)) {

                    List<String> joinColumns = Lists.newArrayList(primaryEntity.getPropertyColumnNames(propertyName));
                    // FIXME we have to assure that join conditions cover all the foreign key column, if not don't generate relation and print a warning log
                    // I have to represent SQLJoinConditions maybe with Map<SQLTable, List<SQLJoinConditions>> to do that easily
                    boolean containsJoinColumn = Iterables.size(Iterables.filter(joinColumns, new Predicate<String>() {
                        @Override
                        public boolean apply(
                            String column)
                        {
                            return column.equalsIgnoreCase(primaryJoinColumn);
                        }
                    })) > 0;

                    if (containsJoinColumn) {
                        relations.add(propertyName);
                    }
                }
            }
        }

        return relations;
    }

    protected AbstractEntityPersister getEntityClass(
        String schema,
        String tableName)
    {
        Map<String, ClassMetadata> metadataMap = sessionFactory.getAllClassMetadata();
        for (String key : metadataMap.keySet()) {
            ClassMetadata metadata = metadataMap.get(key);
            if (metadata instanceof AbstractEntityPersister) {
                AbstractEntityPersister persister = (AbstractEntityPersister) metadata;
                if (persister.getTableName().equalsIgnoreCase(tableName)) { //FIXME check schema? how to retrieve this information from persister using Hibernate API?
                    return persister;
                }
            }
        }

        throw new RuntimeException("No entity found for table " + tableName);
    }

    protected AbstractEntityPersister getEntityClass(
        Class<?> cls)
    {
        AbstractEntityPersister entity = findEntityFromClass(cls);

        if (entity == null) {
            throw new RuntimeException("No entity found for class " + cls.getName());
        }

        return entity;
    }

    //    protected String getDefaultSchema()
    //    {
    //        SessionFactoryImplementor sfi = (SessionFactoryImplementor) getSessionFactory();
    //        String defaultSchemaName = sfi.getSettings().getDefaultSchemaName();
    //        return defaultSchemaName;
    //    }

    private AbstractEntityPersister findEntityFromClass(
        Class<?> cls)
    {
        ClassMetadata metadata = sessionFactory.getClassMetadata(cls);
        if (metadata instanceof AbstractEntityPersister) {
            AbstractEntityPersister persister = (AbstractEntityPersister) metadata;
            return persister;
        }
        return null;
    }

    private boolean existsEntityClass(
        Class<?> cls)
    {
        return findEntityFromClass(cls) != null;
    }

    private Class<?> getPropertyClass(
        AbstractEntityPersister entity,
        String property)
    {
        Type type = entity.getPropertyType(property);
        Class<?> cls = type.getReturnedClass();

        if (type.isCollectionType()) {
            CollectionType collType = (CollectionType) type;
            cls = collType.getElementType((SessionFactoryImplementor) sessionFactory).getReturnedClass();
        }
        return cls;
    }
}
