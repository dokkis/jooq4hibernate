package com.fhoster.jooq4hibernate;

import org.jooq.conf.Settings;

/**
 * Encapsulate the jooqSettings if a specific Settings is needed and maybe in
 * the future will contain specific settings for Hibernate
 * 
 * @author Antonio Gallo
 * 
 */
public class HibernateSettings {

    private Settings jooqSettings;

    public HibernateSettings(Settings jooqSettings) {
        super();
        this.jooqSettings = jooqSettings;
    }

    public Settings jooqSettings()
    {
        return jooqSettings;
    }
}
