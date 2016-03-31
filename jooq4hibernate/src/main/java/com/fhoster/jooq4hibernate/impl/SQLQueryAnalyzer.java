package com.fhoster.jooq4hibernate.impl;

import java.util.Map;
import java.util.Set;

import org.jooq.Record;
import org.jooq.Select;

interface SQLQueryAnalyzer {

	public void analyze(Select<? extends Record> select);
	
    public Set<SQLTable> getTables();

    public Map<SQLTable, Set<SQLJoinCondition>> getJoinConditions();
}
