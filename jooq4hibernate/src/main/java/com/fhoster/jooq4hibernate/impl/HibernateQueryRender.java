package com.fhoster.jooq4hibernate.impl;

import java.util.Iterator;
import java.util.Set;

class HibernateQueryRender {

	private final static String RN                      = System.getProperty("line.separator");
	private final static String RNTAB                   = RN + "\t";
	private final static String QUERY_LIST              = "Query query = session.createSQLQuery(\"%s\")";
	private final static String QUERY_ADDENTITY         = ".addEntity(\"%s\", %s.class)";
	private final static String QUERY_ADDJOIN           = ".addJoin(\"%s\", \"%s\")";
	private final static String QUERY_RESULTTRANSFORMER = ".setResultTransformer( Criteria.DISTINCT_ROOT_ENTITY );";
	private final static String QUERY_RESULT            = "List<%s> queryResult = query.list();";

	protected static String render(
			String sql, Set<HibernateRelation> hibernateRelations)
	{
		StringBuilder hibernateQueryExec = new StringBuilder();
		Iterator<HibernateRelation> iterator = hibernateRelations.iterator();
		HibernateRelation root = iterator.next();

		hibernateQueryExec.append(String.format(QUERY_LIST, sql))
						  .append(RNTAB)
						  .append(String.format(QUERY_ADDENTITY, root.getName(), root.getCls().getSimpleName()));

		while (iterator.hasNext()) {
			HibernateRelation hibernateRelation = iterator.next();
			hibernateQueryExec.append(RNTAB)
							  .append(String.format(QUERY_ADDJOIN, hibernateRelation.getName(), hibernateRelation.getPath()));
		}

		hibernateQueryExec.append(RNTAB)
						  .append(String.format(QUERY_ADDENTITY, root.getName(), root.getCls().getSimpleName()))
						  .append(RNTAB)
						  .append(QUERY_RESULTTRANSFORMER)
						  .append(RN)
						  .append(String.format(QUERY_RESULT, root.getCls().getSimpleName()));

		return hibernateQueryExec.toString();
	}
}
