package com.fhoster.jooq4hibernate.impl;

import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.jdbc.ReturningWork;

import com.fhoster.jooq4hibernate.HibernateDSLContext;
import com.fhoster.jooq4hibernate.HibernateSettings;

public class HibernateDSL {

    public static HibernateDSLContext using(
        final Session session)
    {
        return session.doReturningWork(new ReturningWork<HibernateDSLContext>() {
            @Override
            public HibernateDSLContext execute(
                Connection connection)
                    throws SQLException
            {
                DefaultHibernateSettings settings = new DefaultHibernateSettings(JooqUtil.settings());
                return using(session, settings);
            }
        });
    }

    public static HibernateDSLContext using(
        final Session session,
        final HibernateSettings settings)
    {
        return session.doReturningWork(new ReturningWork<HibernateDSLContext>() {
            @Override
            public HibernateDSLContext execute(
                Connection connection)
                    throws SQLException
            {
                HibernateQueryBuilder queryBuilder = new DefaultHibernateQueryBuilder(session);
                return new DefaultHibernateDSLContext(connection, queryBuilder, settings);
            }
        });
    }
}
