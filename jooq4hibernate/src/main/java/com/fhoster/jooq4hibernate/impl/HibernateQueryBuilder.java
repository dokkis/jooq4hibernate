package com.fhoster.jooq4hibernate.impl;

import java.util.Set;

import org.hibernate.Query;

interface HibernateQueryBuilder {

	public Set<HibernateRelation> findHibernateRelationship(SQLQueryAnalyzer sqlQueryAnalyzer);
	    
    public Query buildQuery(
        String sql,
        Set<HibernateRelation> hibernateRelations);

	public Query buildQueryForCount(String sql);
}
