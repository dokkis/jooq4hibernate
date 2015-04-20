package com.fhoster.jooq4hibernate.impl;

import java.lang.reflect.Field;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Select;
import org.jooq.Table;
import org.jooq.VisitContext;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultVisitListener;

import com.fhoster.jooq4hibernate.HibernateDSLContext;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * Default implementation of SQLQueryAnalyzer using VisitListener.
 * 
 * Open "issues"\question:
 * 	1) Do i use the correct Clause to extract the information that i need?
 * 		Select\join table, excluding any subquery only on the main query, and their join conditions.
 * 		Is it correct check for Clause.SELECT_FROM in the context.clauses() for this purpose?
 * 	2) In clause TABLE_REFERENCE in some cases the queryPart is Table in others TableList
 * 		A simple select with alias specified return Table the same select without alias seems to return TableList with one element inside.
 * 		To extract the table from tableList actually I use the reflection on the superClass to retrieve the wrapperList, assuming that contains Objects implementing Table interface.
 * 	3) For the case of TableList and case of Field I retrieve information needed with reflection. Can i do in some other way?
 *  4) In TABLE_REFERENCE if the alias is not specified, it seems to return "join" as table.getName(). See isValidAlias (I actually exclude "join" as valid name alias, this is dangerous because if for some reason, some table has declared with 'join' alias, this is ignored!).
 *  5) To trigger the VisitListener, I use DSL.using(configuration).renderContext().visit(select); Is it ok? I want to avoid select.getSQL() just to trigger the VisitListener.
 *  6) Just to report, it's not an issue here. For some clauses, if i try to print the queryPart, a stackoverflowexception will be trown.
 * @author Antonio Gallo
 * 
 */
class DefaultSQLQueryAnalyzer extends DefaultVisitListener implements SQLQueryAnalyzer {
	private Set<SQLTable>         tables         = new LinkedHashSet<SQLTable>();
	private Set<SQLJoinCondition> joinConditions = new LinkedHashSet<SQLJoinCondition>();
	private final HibernateDSLContext hdsl;
	
	public DefaultSQLQueryAnalyzer(HibernateDSLContext hdsl){
		this.hdsl=hdsl;
	}
	
	@Override
	public void analyze(Select<Record> select) {
		// FIXME remove LoggerDebugVisitListener
		Configuration configuration = JooqUtil.configuration(hdsl, new LoggerDebugVisitListener(), this);
		DSL.using(configuration).renderContext().visit(select); // Discovery tables and join conditions
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void clauseStart(
			VisitContext context)
	{
		Clause clause = context.clause();
		QueryPart queryPart = context.queryPart();

		boolean clauseIsSelectFrom = Iterables.contains(Lists.newArrayList(context.clauses()), Clause.SELECT_FROM);

		if (clauseIsSelectFrom) {
			if (clause == Clause.TABLE_REFERENCE) {
				if(queryPart instanceof Table){ 
					Table<?> table = (Table<?>) queryPart;
					addTable(table);
				} 
				else{
					//FIXME it can be also TableList (where do not exists join but if it's not defined an alias?)
					List<Table<?>> tables = (List<Table<?>>) field(queryPart, "wrappedList");

					if(tables.size()!=1){ //FIXME is it possible? when and why?
						throw new RuntimeException("Unsupported TableList != 1");
					}
					addTable(tables.get(0));
				}
			}
			else if (clause == Clause.SCHEMA_REFERENCE) {
				Table<?> table = (Table<?>) queryPart;
				SQLTable sqlTable = Iterables.getLast(tables);
				String tableName = table.getName();
				sqlTable.setSchema(table.getSchema().getName());
				sqlTable.setName(tableName);
			}
			else if (clause.equals(Clause.FIELD_REFERENCE)) {
				// FIXME: is the only way to get this information?
				org.jooq.Field<?> field1 = field(queryPart, "field1");
				org.jooq.Field<?> field2 = field(queryPart, "field2");

				Table<?> lhs = field(field1, "table");
				Table<?> rhs = field(field2, "table");

				SQLTable leftTable = getTableFromAlias(lhs.getName());
				String leftColumn = field1.getName();
				SQLTable rightTable = getTableFromAlias(rhs.getName());
				String rightColumn = field2.getName();
				SQLJoinCondition join = new SQLJoinCondition(leftTable, rightTable, leftColumn, rightColumn);
				joinConditions.add(join);
			}
		}
	}

	private void addTable(Table<?> table) {
		SQLTable sqlTable = new SQLTable();
		if (isValidAlias(table)) {
			sqlTable.setAlias(table.getName());
		}
		tables.add(sqlTable);
	}

	private boolean isValidAlias(
			Table<?> table)
	{
		return !table.getName().equals("join"); // FIXME: jOOQ bug? table.getName() is 'join' when an alias is not defined
	}

	private SQLTable getTableFromAlias(
			final String alias)
	{
		Iterable<SQLTable> filteredTables = Iterables.filter(tables, new Predicate<SQLTable>() {
			@Override
			public boolean apply(
					SQLTable table)
			{
				return table.getAlias().equals(alias);
			}
		});
		if (Iterables.size(filteredTables) == 0) {
			throw new RuntimeException("No table found for alias " + alias);
		}
		else if (Iterables.size(filteredTables) != 1) {
			throw new RuntimeException("Duplicate alias " + alias + " in the input query.");
		}
		return filteredTables.iterator().next();
	}

	@SuppressWarnings("unchecked")
	private <T> T field(
			Object obj,
			String name)
	{
		try {
			Field declaredField = obj.getClass().getDeclaredField(name);
			declaredField.setAccessible(true);
			T field = (T) declaredField.get(obj);
			return field;
		}
		catch (Exception e) {
			try{
				Field declaredField = obj.getClass().getSuperclass().getDeclaredField(name);
				declaredField.setAccessible(true);
				T field = (T) declaredField.get(obj);
				return field;
			} catch(Exception ex){
				throw new RuntimeException(ex);
			}
		}
	}

	@Override
	public Set<SQLTable> getTables()
	{
		return tables;
	}

	@Override
	public Set<SQLJoinCondition> getJoinConditions()
	{
		return joinConditions;
	}
}
