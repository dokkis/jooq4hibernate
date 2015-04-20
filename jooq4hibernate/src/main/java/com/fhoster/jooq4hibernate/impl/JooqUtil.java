package com.fhoster.jooq4hibernate.impl;

import org.jooq.Configuration;
import org.jooq.VisitListener;
import org.jooq.conf.ParamType;
import org.jooq.conf.RenderNameStyle;
import org.jooq.conf.Settings;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultVisitListenerProvider;

import com.fhoster.jooq4hibernate.HibernateDSLContext;

public class JooqUtil {
	
	public static Settings settings(){
		Settings jooqSettings = new Settings().withParamType(ParamType.INLINED)
				  							  .withRenderNameStyle(RenderNameStyle.AS_IS);
		return jooqSettings;
	}
	
	public static Configuration configuration(
			HibernateDSLContext hdsl,
			VisitListener... visitListeners)
	{
		Configuration configuration = new DefaultConfiguration();
		configuration.set(hdsl.dialect());
		configuration.set(hdsl.settings().jooqSettings());
		configuration.set(DefaultVisitListenerProvider.providers(visitListeners));
		return configuration;
	}
}
