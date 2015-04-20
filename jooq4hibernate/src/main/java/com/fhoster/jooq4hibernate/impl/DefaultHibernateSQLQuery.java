package com.fhoster.jooq4hibernate.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.transform.ResultTransformer;
import org.jooq.Configuration;
import org.jooq.Record;
import org.jooq.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fhoster.jooq4hibernate.HibernateDSLContext;
import com.fhoster.jooq4hibernate.HibernateSQLQuery;

/**
 * Default implementation of HibernateSQLQuery. It wraps Query class provided by
 * Hibernate.
 * 
 * @author Antonio Gallo
 * 
 */
class DefaultHibernateSQLQuery implements HibernateSQLQuery {
	private final static Logger logger = LoggerFactory.getLogger(DefaultHibernateSQLQuery.class);
	
	private final HibernateDSLContext hdsl;
    private final Select<Record> select;

    private ResultTransformer resultTransformer;
    private boolean readOnly;
    private FlushMode flushMode;
    
    DefaultHibernateSQLQuery(HibernateDSLContext hdsl, Select<Record> select) {
        super();
        this.hdsl = hdsl;
        this.select = select;
        this.resultTransformer = Criteria.DISTINCT_ROOT_ENTITY; 
    }
    
    private Query buildQueryFromJooqStatement(
            Select<Record> select)
    {
    	SQLQueryAnalyzer queryAnalyzer = new DefaultSQLQueryAnalyzer(hdsl);
    	queryAnalyzer.analyze(select);
		
        Set<HibernateRelation> hibernateRelations = hdsl.queryBuilder().findHibernateRelationship(queryAnalyzer);
        
        // Replace select statement after discovery tables
        Configuration configuration = JooqUtil.configuration(hdsl, new SelectAllVisitListener(hibernateRelations));
        select.attach(configuration);
        String sql = select.getSQL();
        
        logger.debug(sql); 

        Query query = hdsl.queryBuilder().buildQuery(sql, hibernateRelations);
        
        query.setResultTransformer(resultTransformer);
        query.setReadOnly(readOnly);
        
        if(flushMode!=null){
        	query.setFlushMode(flushMode);
        }
        
		return query;
    }
    
    private Query buildQueryFromJooqStatementForCount(
            Select<Record> select)
    {
		Configuration configuration = JooqUtil.configuration(hdsl, new SelectCountVisitListener() );
        select.attach(configuration);
        String sql = select.getSQL();
        
        logger.debug(sql);

        Query query = hdsl.queryBuilder().buildQueryForCount(sql);
		return query;
    }
    
    @Override
    public BigInteger count()
    {
    	return (BigInteger) buildQueryFromJooqStatementForCount(select).list().get(0);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> list()
    {
    	return buildQueryFromJooqStatement(select).list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T uniqueResult()
    {
        return (T) buildQueryFromJooqStatement(select).uniqueResult();
    }

    @Override
    public HibernateSQLQuery resultTransformer(
        ResultTransformer resultTransformer)
    {
    	this.resultTransformer = resultTransformer;
        return this;
    }

    @Override
    public HibernateSQLQuery readOnly(
        boolean readOnly)
    {
    	this.readOnly = readOnly;
        return this;
    }

    @Override
    public HibernateSQLQuery flushMode(
        FlushMode flushMode)
    {
    	this.flushMode = flushMode;
        return this;
    }
}
