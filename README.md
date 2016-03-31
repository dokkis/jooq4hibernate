#Jooq4Hibernate project:

This prototypal project born with these two consideration in mind:
	- jOOQ can used to only generate SQL Code (.getSQL() method)
	- Hibernate can execute SQL Code through the SQLQuery API

So at the start i tried this approach:

DSLContext dsl = ...;
String sql = dsl.select().from(...).join(...).on(...).getSQL();
SQLQuery query = session.createSQLQuery(sql);
query = query.addEntity(Entity.class);
query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
List<Entity> result = query.list(); 
or if I expect only one result from the query:
Entity result = query.uniqueResult();

This approach seems to work fine. SQLQuery return the entities with an attached session of hibernate, so if I ask for the associations, hibernate can load it through the proxy making ad-hoc query.

But it's unaccettable for our web application to load only the root entities, because the upper layer ask often for the associations, and replace old implementation based on Criteria with these approach brings to inefficient procedure.

So I searched the SQLQuery API and I found these reference: https://docs.jboss.org/hibernate/orm/4.1/devguide/en-US/html/ch13.html#d5e3571

It's possible to tell to load also the association if we declare explicitly with the method .addJoin(String tableAlias, String path);

So for example if it's envolved a new entity in the query and we want to map it we can write:

query = query.addEntity(Entity.class, "e")
			 .addJoin(Entity2.class, "e.entity2");

In the chapter "Returning multiple entities", is described that to avoid problem with column name duplicates, we should write in the select query {tableAlias1.*}, {tableAlias2.*} and so on.

So after all the first phase of experimentation, I decided to encapsulate all these feature and behaviour in what i called "HibernateDSLContext" that is able to do the following things:
	1) discovery the tables involved in the query (excluding subquery, only the "primary" tables)
	2) discovery all the join conditions (excluding subquery, only on the "primary" tables)
	3) methods:
SelectSelectStep<Record> select() to return the SelectSelectStep without declaring the fields (it's useless declaring fields, please read the following)
<T> List<T> list(Select<?> select) 
<T> T uniqueResult
 list and uniqueResult take in input the select statement generated with jOOQ and build the SQLQuery code using 1 and 2.
		with the tables discovery, I take the first table and use it for the root entity. And then I use them to generate select statement with this form: select {tableAlias1.*}, {tableAlias2.*} from
		with the join conditions, i am able to found (using the Hibernate Session "Metadata" API) the Entity relations between tables

I tested with some cases (simple and complex) and seems to work.

This is the version of the first prototipal code using HibernateDSLContext:

Session session = ...;
HibernateDSLContext hdsl = HibernateDSLContext.using(session);
Select<?> select = hdsl.select().from(...).join(...).on(...);
List<Entity> hdsl.list(select);


Reference to Jooq google group: https://groups.google.com/forum/#!msg/jooq-user/x-NGYEPeetw/oUM-K8JHH6kJ
