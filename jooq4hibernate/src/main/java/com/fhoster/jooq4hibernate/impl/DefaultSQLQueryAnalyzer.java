 package com.fhoster.jooq4hibernate.impl;

import static org.jooq.Clause.SELECT_FROM;
import static org.jooq.Clause.TABLE_JOIN_ON;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Select;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.VisitContext;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultVisitListener;

import com.fhoster.jooq4hibernate.HibernateDSLContext;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

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
    private final Map<String, SQLTable>             tables         = new LinkedHashMap<String, SQLTable>();
    private final Map<SQLTable, Set<SQLJoinCondition>>     joinConditions = new LinkedHashMap<SQLTable, Set<SQLJoinCondition>>();
    private final HibernateDSLContext hdsl;
    private boolean discoveryTableAndJoinConditions = true;

    public DefaultSQLQueryAnalyzer(HibernateDSLContext hdsl) {
        this.hdsl = hdsl;
    }

    @Override
    public void analyze(
        Select<? extends Record> select)
    {
        // FIXME remove LoggerDebugVisitListener
        Configuration configuration = JooqUtil.configuration(hdsl, new LoggerDebugVisitListener(), this);
        DSL.using(configuration).render(select); // Discovery tables and join conditions
    }
    
    @Override
    public void visitEnd(VisitContext context){
    	Clause clause = context.clause();
    	if(clause==Clause.SELECT_FROM){
    		discoveryTableAndJoinConditions = false;
    	}
    }
    
    @Override
    public void visitStart(
        VisitContext context)
    {
        Clause clause = context.clause();
        QueryPart queryPart = context.queryPart();

        Set<Clause> clauseList = Sets.newHashSet(context.clauses());
        
		boolean insideSelectFrom = Iterables.contains(clauseList, SELECT_FROM);
        
        if (discoveryTableAndJoinConditions && insideSelectFrom) {
        	switch(clause)
        	{
		        	case TABLE_ALIAS:
		        		Table<?> table = (Table<?>) queryPart;
		        		SQLTable sqlTable = new SQLTable();
		                String tableAlias = table.getName();
		                sqlTable.setSchema(table.getSchema().getName());
		                sqlTable.setAlias(tableAlias);
		                context.data().put(SQLTable.class, sqlTable);
		                break;
		        	case TABLE_REFERENCE:
		        		if(context.data().containsKey(SQLTable.class)){
		        			sqlTable = (SQLTable) context.data().get(SQLTable.class);
		        			table = (Table<?>) queryPart;
		        			sqlTable.setName(table.getName());
		        		} else{
			            	table = (Table<?>) queryPart;
			            	sqlTable = new SQLTable();
			                String tableName = table.getName();
			                sqlTable.setSchema(table.getSchema().getName());
			                sqlTable.setName(tableName);
		        		}
		        		
		        		context.data().remove(SQLTable.class);
		        		
		        		if(tables.containsKey(sqlTable.getAlias())){
		        			throw new RuntimeException("Duplicate alias " + sqlTable.getAlias() + " in the input query.");
		        		}
		        		
		        		tables.put(sqlTable.getAlias(), sqlTable);
		        		break;
		        	case CONDITION_COMPARISON:
		        		boolean insideJoinOn = Iterables.contains(clauseList, TABLE_JOIN_ON);
		                
		        		if(insideJoinOn){
			        		// FIXME: is the only way to get this information?
			                org.jooq.Field<?> field1 = field(queryPart, "field1");
			                org.jooq.Field<?> field2 = field(queryPart, "field2");
			                
			                //FIXME what if not? if field is value?
			                if (TableField.class.isAssignableFrom(field1.getClass()) && TableField.class.isAssignableFrom(field2.getClass())) {
			                    TableField<?, ?> tableField1 = TableField.class.cast(field1);
			                    TableField<?, ?> tableField2 = TableField.class.cast(field2);
			                    newJoinCondition(tableField1, tableField2);
			                }
		        		}
		                break;
	                default: 
	                	break;
	        	}
        }
    }
    
    private void newJoinCondition(TableField<?, ?> leftTableField, TableField<?, ?> rightTableField) {
    	SQLTable sqlTable = Iterables.getLast(tables.values());
    	Table<?> lTable = leftTableField.getTable();
        Table<?> rTable = rightTableField.getTable();
        
        if(!tables.containsKey(lTable.getName())){
        	throw new RuntimeException("No table found for alias " + lTable.getName());
        }
        else if(!tables.containsKey(rTable.getName())) {
        	throw new RuntimeException("No table found for alias " + rTable.getName());
        }
        	
        SQLTable leftTable = tables.get(lTable.getName());
        String leftColumn = leftTableField.getName();
        SQLTable rightTable = tables.get(rTable.getName());
        String rightColumn = rightTableField.getName();
    	
    	final SQLJoinCondition joinCondition = new SQLJoinCondition(leftTable, rightTable, leftColumn, rightColumn);
    	
    	final Set<SQLJoinCondition> joins;
    	if(joinConditions.containsKey(sqlTable)){
    		joins = joinConditions.get(sqlTable);
    	} else{
    		joins = new LinkedHashSet<SQLJoinCondition>();
			joinConditions.put(sqlTable, joins);
    	}
    	joins.add(joinCondition);
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
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<SQLTable> getTables()
    {
        return Sets.newHashSet(tables.values());
    }

    @Override
    public Map<SQLTable, Set<SQLJoinCondition>> getJoinConditions()
    {
        return joinConditions;
    }
}
