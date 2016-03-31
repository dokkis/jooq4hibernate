package com.fhoster.jooq4hibernate;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.SelectSelectStep;

/**
 * HibernateDSLContext is the entry point of jooq4hibernate.
 * 
 * An example is given here:
 * 
 * HibernateDSLContext hdsl = HibernateDSL.using(session);
 * 
 * Select<Record> select = hdsl.select(MY_TABLE).where(MY_TABLE.ID.eq(1));
 * 
 * List<MY_TABLE_ENTITY> entityList = hdsl.createQuery(select).list();
 * 
 * or
 * 
 * MY_TABLE_ENTITY entity = hdsl.createQuery(select).uniqueResult();
 * 
 * Where MY_TABLE is the metadata model of jOOQ for a generic table and
 * MY_TABLE_ENTITY is the relative Hibernate Entity of the same table
 * 
 * @author Antonio Gallo
 * 
 */
public interface HibernateDSLContext {

    public SelectSelectStep<? extends Record> select();
    
    public DSLContext jooqContext();

    public HibernateSQLQuery createQuery(
        Select<? extends Record> select);
    
    public HibernateSettings settings();
    
    public SQLDialect dialect();

}
