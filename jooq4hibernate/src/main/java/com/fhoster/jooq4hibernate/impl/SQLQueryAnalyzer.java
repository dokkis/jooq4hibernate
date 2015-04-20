package com.fhoster.jooq4hibernate.impl;

import java.util.Set;

import org.jooq.Record;
import org.jooq.Select;

interface SQLQueryAnalyzer {

	public void analyze(Select<Record> select);
	
    public Set<SQLTable> getTables();

    public Set<SQLJoinCondition> getJoinConditions();
}
