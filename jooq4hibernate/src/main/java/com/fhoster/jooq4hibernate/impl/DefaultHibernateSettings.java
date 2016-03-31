package com.fhoster.jooq4hibernate.impl;

import org.jooq.conf.Settings;

import com.fhoster.jooq4hibernate.HibernateSettings;

/**
 * Encapsulate the jooqSettings if a specific Settings is needed and maybe in
 * the future will contain specific settings for Hibernate
 * 
 * @author Antonio Gallo
 * 
 */
class DefaultHibernateSettings implements HibernateSettings{

    private Settings jooqSettings;

    public DefaultHibernateSettings(Settings jooqSettings) {
        super();
        this.jooqSettings = jooqSettings;
    }

    public Settings jooqSettings()
    {
        return jooqSettings;
    }
}
