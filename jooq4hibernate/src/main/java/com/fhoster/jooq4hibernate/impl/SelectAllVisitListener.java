package com.fhoster.jooq4hibernate.impl;

import java.util.Set;

import org.jooq.Clause;
import org.jooq.VisitContext;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultVisitListener;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;

/**
 * Replace the select into the form: select {table1.*}, {table2.*} from to
 * avoid problem with table that have same column name see this link for
 * more information: https://docs.jboss.org/hibernate/orm/4.1/devguide
 * /en-US/html/ch13.html#d5e3571
 * 
 * @return {table1.*}, {table2.*}, ..., {tableN.*}
 */
public class SelectAllVisitListener extends DefaultVisitListener {
	private final Set<HibernateRelation> hibernateRelations;
	
	public SelectAllVisitListener(Set<HibernateRelation> hibernateRelations) {
		super();
		this.hibernateRelations = hibernateRelations;
	}

	@Override
	public void visitStart(
			VisitContext context)
	{
		Clause clause = context.clause();
		if (clause == Clause.SELECT_SELECT) {
			context.queryPart(DSL.queryPart(""));
			context.renderContext().sql(buildSelectFields());
		}
	}

	private String buildSelectFields()
	{
		if (hibernateRelations.size() == 0) {
			throw new RuntimeException("No tables found.");
		}
		
		Iterable<String> tableAlias = Iterables.transform(hibernateRelations, new Function<HibernateRelation, String>() {
			@Override
			public String apply(
					HibernateRelation relation)
			{
				return String.format("{%s.*}", relation.getName());
			}
		});

		return Joiner.on(", ").join(tableAlias);
	}
}
