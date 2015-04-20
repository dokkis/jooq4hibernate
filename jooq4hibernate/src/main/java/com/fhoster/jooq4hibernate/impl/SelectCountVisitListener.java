package com.fhoster.jooq4hibernate.impl;

import org.jooq.Clause;
import org.jooq.VisitContext;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultVisitListener;

public class SelectCountVisitListener extends DefaultVisitListener {
	public static final String COUNT_AS = "num";
	
	@Override
	public void visitStart(
			VisitContext context)
	{
		Clause clause = context.clause();
		if (clause == Clause.SELECT_SELECT) {
			context.queryPart(DSL.queryPart(""));
			context.renderContext().sql(String.format("count(*) as %s", COUNT_AS));
		}
	}
}
