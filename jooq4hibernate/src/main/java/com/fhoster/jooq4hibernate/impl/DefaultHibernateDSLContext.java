package com.fhoster.jooq4hibernate.impl;

import java.sql.Connection;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.SelectSelectStep;
import org.jooq.impl.DSL;
import org.jooq.tools.jdbc.JDBCUtils;

import com.fhoster.jooq4hibernate.HibernateDSLContext;
import com.fhoster.jooq4hibernate.HibernateSQLQuery;
import com.fhoster.jooq4hibernate.HibernateSettings;

/**
 * Default implementation of HibernateDSLContext
 * 
 * @author Antonio Gallo
 * 
 */
class DefaultHibernateDSLContext implements HibernateDSLContext {
    private final Connection connection;
    private final HibernateQueryBuilder queryBuilder;
    private final HibernateSettings     settings;
    private final SQLDialect dialect;
    
    protected DefaultHibernateDSLContext(Connection connection, HibernateQueryBuilder queryBuilder, HibernateSettings settings) {
        this.connection = connection;
        this.queryBuilder = queryBuilder;
        this.settings = settings;
        this.dialect = JDBCUtils.dialect(connection);
    }

    @Override
    public SelectSelectStep<? extends Record> select()
    {
        DSLContext dslContext = DSL.using(connection, settings.jooqSettings());
        return dslContext.select();
    }
    
    @Override
    public DSLContext jooqContext(){
    	DSLContext dslContext = DSL.using(connection, settings.jooqSettings());
        return dslContext;
    }

    @Override
    public HibernateSQLQuery createQuery(
        Select<? extends Record> select)
    {
        final HibernateSQLQuery query = new DefaultHibernateSQLQuery(this, select, queryBuilder);
        return query;
    }

    @Override
    public HibernateSettings settings()
    {
        return settings;
    }
    
    public HibernateQueryBuilder queryBuilder(){
    	return queryBuilder;
    }

	@Override
	public SQLDialect dialect() {
		return dialect;
	}
}
