package com.fhoster.jooq4hibernate.impl;

import java.util.HashSet;
import java.util.Iterator;
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

abstract class HibernateUtil {

    private final static Logger logger = LoggerFactory.getLogger(HibernateUtil.class);

    protected static Set<HibernateRelation> findHibernateRelationship(
        SessionFactory sessionFactory,
        Set<SQLTable> tables,
        Set<SQLJoinCondition> joinConditions)
    {
        Set<HibernateRelation> hibernateRelations = new LinkedHashSet<HibernateRelation>();
        Set<String> aliasList = new LinkedHashSet<String>();

        SQLTable rootTable = Iterables.get(tables, 0);

        AbstractEntityPersister entityRoot = getEntityClass(sessionFactory, rootTable.getSchema(), rootTable.getName());
        aliasList.add(rootTable.getAlias());

        HibernateRelation rootHibernateRelation = new HibernateRelation(rootTable.getAlias(), rootTable.getAlias(), entityRoot.getMappedClass());
        hibernateRelations.add(rootHibernateRelation);

        for (SQLJoinCondition join : joinConditions) {
            SQLTable leftTable = join.getLeftTable();
            SQLTable rightTable = join.getRightTable();

            System.out.println("Analyze join between " + leftTable + " and " + rightTable);

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
            AbstractEntityPersister primaryEntity = getEntityClass(sessionFactory, primary.getSchema(), primary.getName());
            AbstractEntityPersister secondaryEntity = getEntityClass(sessionFactory, secondary.getSchema(), secondary.getName());

            aliasList.add(secondaryAlias);
            Set<String> relations = findRelationProperties(sessionFactory, primaryEntity, secondaryEntity, joinColumn);

            if (relations.isEmpty()) {
                logger.warn("Join Condition found but not mapped in HibernateRelation: " + join);
            }

            for (String relationProperty : relations) {
                String relationPath = Joiner.on(DOT).join(primaryAlias, relationProperty);
                HibernateRelation hibernateRelation = new HibernateRelation(secondaryAlias, relationPath, secondaryEntity.getMappedClass());
                hibernateRelations.add(hibernateRelation);
            }
        }

        System.out.println("HibernateRelations found: " + hibernateRelations);

        return hibernateRelations;
    }

    protected static Set<String> findRelationProperties(
    	final SessionFactory sessionFactory,
        final AbstractEntityPersister primaryEntity,
        final AbstractEntityPersister secondaryEntityToResearch,
        final String primaryJoinColumn)
    {
        Set<String> relations = new HashSet<String>();

        String tableNameToResearch = secondaryEntityToResearch.getTableName();

        String[] propertyNames = primaryEntity.getPropertyNames();
        for (String propertyName : propertyNames) {
            Class<?> cls = getPropertyClass(sessionFactory, primaryEntity, propertyName);

            if (existsEntityClass(sessionFactory, cls)) {
                AbstractEntityPersister persister = getEntityClass(sessionFactory, cls);
                String tableName = persister.getTableName();
                if (tableName.equals(tableNameToResearch)) {
                	
                	List<String> joinColumns = Lists.newArrayList(primaryEntity.getPropertyColumnNames(propertyName));
                	boolean containsJoinColumn = Iterables.size(Iterables.filter(joinColumns, new Predicate<String>() {
						@Override
						public boolean apply(String column) {
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

    protected static AbstractEntityPersister getEntityClass(
        SessionFactory sessionFactory,
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

    protected static AbstractEntityPersister getEntityClass(
        SessionFactory sessionFactory,
        Class<?> cls)
    {
        AbstractEntityPersister entity = findEntityFromClass(sessionFactory, cls);

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

    private static AbstractEntityPersister findEntityFromClass(
        SessionFactory sessionFactory,
        Class<?> cls)
    {
        ClassMetadata metadata = sessionFactory.getClassMetadata(cls);
        if (metadata instanceof AbstractEntityPersister) {
            AbstractEntityPersister persister = (AbstractEntityPersister) metadata;
            return persister;
        }
        return null;
    }

    private static boolean existsEntityClass(
        SessionFactory sessionFactory,
        Class<?> cls)
    {
        return findEntityFromClass(sessionFactory, cls) != null;
    }

    private static Class<?> getPropertyClass(
        SessionFactory sessionFactory,
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

    protected final static String RN                      = "\r\n";
    protected final static String RNTAB                   = RN + "\t";
    protected final static String QUERY_LIST              = "Query query = session.createSQLQuery(sql)";
    protected final static String QUERY_ADDENTITY         = ".addEntity(\"%s\", %s.class)";
    protected final static String QUERY_ADDJOIN           = ".addJoin(\"%s\", \"%s\")";
    protected final static String QUERY_RESULTTRANSFORMER = ".setResultTransformer( Criteria.DISTINCT_ROOT_ENTITY );";
    protected final static String QUERY_RESULT            = "List<%s> queryResult = query.list();";
    protected final static String DOT                     = ".";
    protected final static String SELECT_ALIAS            = "{%s.*}, ";
    protected final static String SELECT_FROM             = "select %s from";
    protected final static String ALL_CHAR                = ".*?";

    protected static String buildHibernateQueryExecution(
        Set<HibernateRelation> hibernateRelations)
    {
        StringBuilder hibernateQueryExec = new StringBuilder();
        Iterator<HibernateRelation> iterator = hibernateRelations.iterator();

        hibernateQueryExec.append(QUERY_LIST);
        hibernateQueryExec.append(RNTAB);
        HibernateRelation root = iterator.next();
        hibernateQueryExec.append(String.format(QUERY_ADDENTITY, root.getName(), root.getCls().getSimpleName()));

        while (iterator.hasNext()) {
            HibernateRelation hibernateRelation = iterator.next();
            hibernateQueryExec.append(RNTAB);
            hibernateQueryExec.append(String.format(QUERY_ADDJOIN, hibernateRelation.getName(), hibernateRelation.getPath()));
        }

        hibernateQueryExec.append(RNTAB);
        hibernateQueryExec.append(String.format(QUERY_ADDENTITY, root.getName(), root.getCls().getSimpleName()));
        hibernateQueryExec.append(RNTAB);

        hibernateQueryExec.append(QUERY_RESULTTRANSFORMER);
        hibernateQueryExec.append(RN);
        hibernateQueryExec.append(String.format(QUERY_RESULT, root.getCls().getSimpleName()));

        return hibernateQueryExec.toString();
    }
}
