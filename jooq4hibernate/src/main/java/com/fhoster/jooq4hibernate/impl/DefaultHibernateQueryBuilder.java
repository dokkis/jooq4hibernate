package com.fhoster.jooq4hibernate.impl;

import java.util.Iterator;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default implementation of HibernateQueryBuilder
 * 
 * @author Antonio Gallo
 * 
 */
class DefaultHibernateQueryBuilder implements HibernateQueryBuilder {

    protected Session session;
    private final static Logger logger = LoggerFactory.getLogger(DefaultHibernateQueryBuilder.class);

    public DefaultHibernateQueryBuilder(Session session) {
        this.session = session;
    }

    @Override
    public Set<HibernateRelation> findHibernateRelationship(SQLQueryAnalyzer sqlQueryAnalyzer){
    	HibernateUtil hibernateUtil = HibernateUtil.using(session.getSessionFactory());
    	return hibernateUtil.findHibernateRelationship(sqlQueryAnalyzer.getTables(), sqlQueryAnalyzer.getJoinConditions());
    }
    
    @Override
    public Query buildQuery(
        String sql, Set<HibernateRelation> hibernateRelations)
    {
        if(logger.isDebugEnabled()){ // To avoid unnecessary elaboration in building SQLQuery
        	logger.debug(HibernateQueryRender.render(sql, hibernateRelations));
        }
        
        SQLQuery query = session.createSQLQuery(sql);
        Iterator<HibernateRelation> iterator = hibernateRelations.iterator();
        HibernateRelation rootEntity = iterator.next();

        query = query.addEntity(rootEntity.getName(), rootEntity.getCls());
        while (iterator.hasNext()) {
            HibernateRelation hibernateRelation = iterator.next();
            query = query.addJoin(hibernateRelation.getName(), hibernateRelation.getPath());
        }
        query = query.addEntity(rootEntity.getName(), rootEntity.getCls());

        return query;
    }

	@Override
	public Query buildQueryForCount(String sql) {
		SQLQuery query = session.createSQLQuery(sql);
		query.addScalar(SelectCountVisitListener.COUNT_AS);
		return query;
	}
}
