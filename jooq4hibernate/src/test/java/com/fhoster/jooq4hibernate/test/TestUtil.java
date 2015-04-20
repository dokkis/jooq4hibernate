package com.fhoster.jooq4hibernate.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.jdbc.Work;


public class TestUtil {
	private TestUtil() {

	}
	
	public static SessionFactory buildSessionFactory(){
		Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
		// before 4.3
//		ServiceRegistryBuilder builder = new ServiceRegistryBuilder().applySettings(configuration.getProperties());
//		sessionFactory = configuration.buildSessionFactory(builder.buildServiceRegistry());
		// after 4.3
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
		SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());
		return sessionFactory;
	}

	public static void setupDB(final SessionFactory sessionFactory, final String setupFile){
		Session session = sessionFactory.openSession();
		try{
			session.doWork(new Work() {
				@Override
				public void execute(Connection connection) throws SQLException {
					setup(connection, setupFile);
				}
			});
		} finally{
			session.close();
		}
	}

	private static void setup(Connection connection, String setupFile) throws SQLException {
		Statement st = null;
		try {

			IDatabaseConnection c = new DatabaseConnection(connection);
			Connection conn = c.getConnection();
			conn.setAutoCommit(false);
			st = conn.createStatement();

			FlatXmlDataSetBuilder builder = getFlatXmlDataSetBuilder();
			IDataSet data = builder.build(AbstractHibernateDSLContextTest.class.getResourceAsStream(setupFile));

			DatabaseOperation.CLEAN_INSERT.execute(c, data);

			connection.commit();
		}
		catch(Exception ex){
			ex.printStackTrace();
			throw new RuntimeException();
		}
		finally {
			st.close();
		}
	}
	
	public static void assertLazyFetch(Object obj){
		assertThat(Hibernate.isInitialized(obj)).isEqualTo(false);
	}
	
	public static void assertEagerFetch(Object obj){
		assertThat(Hibernate.isInitialized(obj)).isEqualTo(true);
	}

	private static FlatXmlDataSetBuilder getFlatXmlDataSetBuilder()
	{
		FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
		builder.setColumnSensing(true);
		return builder;
	}
}