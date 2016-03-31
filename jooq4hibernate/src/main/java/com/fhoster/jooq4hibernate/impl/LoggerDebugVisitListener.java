package com.fhoster.jooq4hibernate.impl;

import org.jooq.Clause;
import org.jooq.QueryPart;
import org.jooq.Table;
import org.jooq.VisitContext;
import org.jooq.impl.DefaultVisitListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

class LoggerDebugVisitListener extends DefaultVisitListener {

	private final static Logger logger = LoggerFactory.getLogger(DefaultHibernateDSLContext.class);

    int level = 0;

    enum ASTContext {
        CLAUSE_START,
        CLAUSE_END,
        VISIT_START,
        VISIT_END
    }

    @Override
    public void clauseEnd(
        VisitContext context)
    {
        level--;
        printVisitContext(context, ASTContext.CLAUSE_END);
    }

    @Override
    public void clauseStart(
        VisitContext context)
    {
        printVisitContext(context, ASTContext.CLAUSE_START);
        level++;
    }

    @Override
    public void visitStart(
        VisitContext context)
    {
        printVisitContext(context, ASTContext.VISIT_START);
        level++;
    }

    @Override
    public void visitEnd(
        VisitContext context)
    {
        level--;
        printVisitContext(context, ASTContext.VISIT_END);
    }

    private void printVisitContext(
        VisitContext context,
        ASTContext astContext)
    {
        StringBuilder str = new StringBuilder();
        str.append(astContext);
        str.append(Strings.repeat(" ", 20 - astContext.toString().length())); // to align the print log
        str.append(Strings.repeat(" ", level * 3)); // more readable than \t

        Clause clause = context.clause();
        QueryPart queryPart = context.queryPart();
        if (queryPart instanceof Table) {
            Table<?> table = (Table<?>) queryPart;
            str.append(String.format("%s, %s, %s, %s, %s", clause, table.getName(), table.getSchema(), queryPart, queryPart.getClass()));
        }
        else {
            str.append(String.format("%s, %s", clause, queryPart.getClass()));
        }

        logger.trace(str.toString());
    }
}
