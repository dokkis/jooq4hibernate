package com.fhoster.jooq4hibernate;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.FlushMode;
import org.hibernate.transform.ResultTransformer;

/**
 * Return type of the createQuery of HibernateDSLContext.
 * 
 * It encapsulate Query interface of Hibernate and expose methods to retrieve a
 * list of entities, a unique entity and to set some properties like readOnly,
 * the flushMode or a custom resultTransformer
 * 
 * The default resultTransformer if it's not specified is
 * Criteria.DISTINCT_ROOT_ENTITY
 * 
 * Example:
 * 
 * HibernateDSLContext hdsl = HibernateDSL.using(session);
 * 
 * List<MY_TABLE_ENTITY> entityList =
 * hdsl.createQuery(...).readOnly(true).resultTransformer
 * (Criteria.ROOT_ENTITY).list();
 * 
 * @author Antonio Gallo
 * 
 */
public interface HibernateSQLQuery {

    /**
     * @return list of entities
     */
    public <T> List<T> list();

    /**
     * @return unique entity
     */
    public <T> T uniqueResult();
    
    /**
     * 
     * @return count of query rows
     */
    public BigInteger count();

    /**
     * Set the resultTransformer, if not specified is
     * Criteria.DISTINCT_ROOT_ENTITY by default (see Hibernate docs)
     * 
     * @param resultTransformer
     * @return the HibernateSQLQuery
     */
    public HibernateSQLQuery resultTransformer(
        ResultTransformer resultTransformer);

    /**
     * Set the mode readOnly for the entities returned to the Query (see
     * Hibernate docs)
     * 
     * @param readOnly
     * @return the HibernateSQLQuery
     */
    public HibernateSQLQuery readOnly(
        boolean readOnly);

    /**
     * Set the flushMode (see Hibernate docs)
     * 
     * @param flushMode
     * @return
     */
    public HibernateSQLQuery flushMode(
        FlushMode flushMode);

}