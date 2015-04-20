package com.fhoster.jooq4hibernate.test;

import static com.fhoster.jooq4hibernate.test.TestUtil.assertEagerFetch;
import static com.fhoster.jooq4hibernate.test.TestUtil.assertLazyFetch;
import static com.fhoster.jooq4hibernate.test.TestUtil.setupDB;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jooq.Record;
import org.jooq.Select;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fhoster.jooq4hibernate.HibernateDSLContext;
import com.fhoster.jooq4hibernate.HibernateSQLQuery;
import com.fhoster.jooq4hibernate.impl.HibernateDSL;
import com.fhoster.jooq4hibernate.jooq.tables.Address;
import com.fhoster.jooq4hibernate.jooq.tables.Employee;

public abstract class AbstractHibernateDSLContextTest {

	private static SessionFactory sessionFactory;
	private static Session session;
	private static HibernateDSLContext hdsl;

	final Employee e1; 
	final Address a1;
	
	public AbstractHibernateDSLContextTest(Employee e1, Address a1) {
		super();
		this.e1 = e1;
		this.a1 = a1;
	}
	
	@BeforeClass
	public static void init() {
		sessionFactory = TestUtil.buildSessionFactory();
		setupDB(sessionFactory, "/setup.db.xml");
		session = sessionFactory.openSession();
		hdsl = HibernateDSL.using(session);
	}
	
	@AfterClass
	public static void end(){
		session.close();
	}
	
	@Before
	public void clearSession(){
		session.clear();
	}
	
	@Test
	public void testCount(){
		Select<Record> select = hdsl.select().from(e1);
		BigInteger count = hdsl.createQuery(select).count();
		assertThat(count.intValue()).isEqualTo(2);
	}
	
	@Test
	public void testRootEntityListWithoutJoin(){
		Select<Record> select = hdsl.select().from(e1);
		List<com.fhoster.jooq4hibernate.hibernate.Employee> employees = hdsl.createQuery(select).list();
		assertThat(employees.size()).isEqualTo(2);
		
		for(com.fhoster.jooq4hibernate.hibernate.Employee employee : employees){
			assertLazyFetch(employee.getCurrentAddress());
			assertLazyFetch(employee.getPermanentAddress());
		}
	}
	
	@Test
	public void testCountThenSelectAll(){
		Select<Record> select = hdsl.select().from(e1);
		HibernateSQLQuery query = hdsl.createQuery(select);
		BigInteger count = query.count();
		List<com.fhoster.jooq4hibernate.hibernate.Employee> employees = query.list();
		
		assertThat(count.intValue()).isEqualTo(employees.size());
	}
	
	@Test
	public void testRootEntityUniqueResultWithoutJoin(){
		Select<Record> select = hdsl.select().from(e1).where(e1.ID_EMPLOYEE.eq(1L));
		com.fhoster.jooq4hibernate.hibernate.Employee employee = hdsl.createQuery(select).uniqueResult();

		assertLazyFetch(employee.getCurrentAddress());
		assertLazyFetch(employee.getPermanentAddress());
	}
	
	@Test
	public void testRootEntityUniqueResultDoubleAssociationFirstJoinFetched(){
		Select<Record> select = hdsl.select().from(e1)
				.join(a1).on(a1.ID_ADDRESS.eq(e1.CURRENT_ADDRESS_ID))
				.where(e1.ID_EMPLOYEE.eq(1L));
		
		com.fhoster.jooq4hibernate.hibernate.Employee employee = hdsl.createQuery(select).uniqueResult();
		assertEagerFetch(employee.getCurrentAddress());
		assertLazyFetch(employee.getPermanentAddress());
	}
	
	@Test
	public void testRootEntityUniqueResultDoubleAssociationSecondJoinFetched(){
		Select<Record> select = hdsl.select().from(e1)
				.join(a1).on(a1.ID_ADDRESS.eq(e1.PERMANENT_ADDRESS_ID))
				.where(e1.ID_EMPLOYEE.eq(1L));
		
		com.fhoster.jooq4hibernate.hibernate.Employee employee = hdsl.createQuery(select).uniqueResult();
		assertEagerFetch(employee.getPermanentAddress());
		assertLazyFetch(employee.getCurrentAddress());
	}
	
	@Test
	public void testRootEntityUniqueResultDoubleAssociationBothJoinFetched(){
		Select<Record> select = hdsl.select().from(e1)
				.join(a1).on(a1.ID_ADDRESS.eq(e1.PERMANENT_ADDRESS_ID).or(a1.ID_ADDRESS.eq(e1.CURRENT_ADDRESS_ID)))
				.where(e1.ID_EMPLOYEE.eq(1L));
		
		com.fhoster.jooq4hibernate.hibernate.Employee employee = hdsl.createQuery(select).uniqueResult();
		assertEagerFetch(employee.getPermanentAddress());
		assertEagerFetch(employee.getCurrentAddress());
	}
	
	@Test
	public void testRootEntityListDoubleAssociationFirstJoinFetched(){
		Select<Record> select = hdsl.select().from(e1)
				.join(a1).on(a1.ID_ADDRESS.eq(e1.CURRENT_ADDRESS_ID))
				.where(e1.ID_EMPLOYEE.eq(1L));
		
		List<com.fhoster.jooq4hibernate.hibernate.Employee> employees = hdsl.createQuery(select).list();
		for(com.fhoster.jooq4hibernate.hibernate.Employee employee : employees){
			assertEagerFetch(employee.getCurrentAddress());
			assertLazyFetch(employee.getPermanentAddress());
		}
	}
	
	@Test
	public void testRootEntityListDoubleAssociationSecondJoinFetched(){
		Select<Record> select = hdsl.select().from(e1)
				.join(a1).on(a1.ID_ADDRESS.eq(e1.PERMANENT_ADDRESS_ID))
				.where(e1.ID_EMPLOYEE.eq(1L));
		
		List<com.fhoster.jooq4hibernate.hibernate.Employee> employees = hdsl.createQuery(select).list();
		for(com.fhoster.jooq4hibernate.hibernate.Employee employee : employees){
			assertEagerFetch(employee.getPermanentAddress());
			assertLazyFetch(employee.getCurrentAddress());
		}
	}
	
	@Test
	public void testRootEntityListDoubleAssociationBothJoinFetched(){
		Select<Record> select = hdsl.select().from(e1)
				.join(a1).on(a1.ID_ADDRESS.eq(e1.PERMANENT_ADDRESS_ID).or(a1.ID_ADDRESS.eq(e1.CURRENT_ADDRESS_ID)))
				.where(e1.ID_EMPLOYEE.eq(1L));
		
		List<com.fhoster.jooq4hibernate.hibernate.Employee> employees = hdsl.createQuery(select).list();
		for(com.fhoster.jooq4hibernate.hibernate.Employee employee : employees){
			assertEagerFetch(employee.getPermanentAddress());
			assertEagerFetch(employee.getCurrentAddress());
		}
	}
	
	/* TODO 
	 	review and expand the test domain
	 	review and fill the database setup
	other tests:
		with the other entities
		test on entity with same column name
		test for value read instead of only asserting fetched lazy or eager 
		test with query that contains subquery
		and so on
	*/
}
