package com.gitlab.zloster;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.CacheMode;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.jdbc.connections.internal.DatasourceConnectionProviderImpl;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.service.ServiceRegistry;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class NoTransactions {

	public static void main(String[] args) {
		NoTransactions noT = new NoTransactions();
		// configure and instantiate the session
		noT.take1();
		
		// load and call update of the object again.
		// But this time enable updates outside transactions and call flush(). New since 5.2
		noT.take2();
		
		// TODO open new session and read the updated object
	}

	private void take1() {
		SessionFactory sessionFactory = buildSessionFactory();

		Session session = sessionFactory.openSession();

		session.setDefaultReadOnly(false);
		session.setCacheMode(CacheMode.NORMAL);
		session.setHibernateFlushMode(FlushMode.AUTO);

		// load and call update of the object
		try {
			World oneWorld = session.get(com.gitlab.zloster.World.class, 1);
			int randomNumberFromDB = oneWorld.getRandomNumber();
			int newRandomNumber = (randomNumberFromDB * 2) + 1;
			System.out.println("Take1: " + randomNumberFromDB + "; " + newRandomNumber);
			oneWorld.setRandomNumber(newRandomNumber);

			session.saveOrUpdate(requireNonNull(oneWorld));

// We are not forcing the flush!
// If we force the flush() than we will get exception that there is no active transaction
//			session.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	private SessionFactory buildSessionFactory() {
		final Configuration configuration = new Configuration();
		configuration.setProperty(AvailableSettings.CURRENT_SESSION_CONTEXT_CLASS, "managed");
		configuration.setProperty(AvailableSettings.USE_SQL_COMMENTS, Boolean.toString(true));
		configuration.setProperty(AvailableSettings.USE_GET_GENERATED_KEYS, "true");
		configuration.setProperty(AvailableSettings.GENERATE_STATISTICS, "true");
		configuration.setProperty(AvailableSettings.USE_REFLECTION_OPTIMIZER, "true");
		configuration.setProperty(AvailableSettings.ORDER_UPDATES, "true");
		configuration.setProperty(AvailableSettings.ORDER_INSERTS, "true");
		configuration.setProperty(AvailableSettings.USE_NEW_ID_GENERATOR_MAPPINGS, "true");
		configuration.setProperty("jadira.usertype.autoRegisterUserTypes", "true");
		configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");

		// Add entity
		configuration.addAnnotatedClass(com.gitlab.zloster.World.class);

		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setUrl("jdbc:mysql://localhost:3306/hello_world");
		dataSource.setUser("benchmarkdbuser");
		dataSource.setPassword("benchmarkdbpass");

		Map<String, String> dataSourceConfiguration = new HashMap<>();
		dataSourceConfiguration.put("url", "jdbc:mysql://localhost:3306/hello_world");
		dataSourceConfiguration.put("user", "benchmarkdbuser");
		dataSourceConfiguration.put("password", "benchmarkdbpass");
		dataSourceConfiguration.put("driverClass", "com.mysql.jdbc.Driver");

		final DatasourceConnectionProviderImpl connectionProvider = new DatasourceConnectionProviderImpl();
		connectionProvider.setDataSource(dataSource);
		connectionProvider.configure(dataSourceConfiguration);

		final ServiceRegistry registry = new StandardServiceRegistryBuilder()
				.addService(ConnectionProvider.class, connectionProvider)
				.applySettings(configuration.getProperties()).build();

		return configuration.buildSessionFactory(registry);
	}

	private void take2() {
		SessionFactory sessionFactoryUpdateOutsideTransaction = buildSessionFactoryAllowUpdatesOutsideTransaction();
		Session secondSession = sessionFactoryUpdateOutsideTransaction.openSession();

		try {
			World oneWorld = secondSession.get(com.gitlab.zloster.World.class, 1);
			int randomNumberFromDB = oneWorld.getRandomNumber();
			int newRandomNumber = (randomNumberFromDB * 2) + 1;
			System.out.println("Take2: " + randomNumberFromDB + "; " + newRandomNumber);
			oneWorld.setRandomNumber(newRandomNumber);

			secondSession.saveOrUpdate(requireNonNull(oneWorld));

//If we don't force the flush() here there will be NO FLUSHED data at all
			secondSession.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			secondSession.close();
		}
	}

	private SessionFactory buildSessionFactoryAllowUpdatesOutsideTransaction() {
		final Configuration configuration = new Configuration();
		/**
		 * This is the important bit. See the Javadoc of the setting. Since 5.2
		 */
		configuration.setProperty(AvailableSettings.ALLOW_UPDATE_OUTSIDE_TRANSACTION, "true");
		configuration.setProperty(AvailableSettings.CURRENT_SESSION_CONTEXT_CLASS, "managed");
		configuration.setProperty(AvailableSettings.USE_SQL_COMMENTS, Boolean.toString(true));
		configuration.setProperty(AvailableSettings.USE_GET_GENERATED_KEYS, "true");
		configuration.setProperty(AvailableSettings.GENERATE_STATISTICS, "true");
		configuration.setProperty(AvailableSettings.USE_REFLECTION_OPTIMIZER, "true");
		configuration.setProperty(AvailableSettings.ORDER_UPDATES, "true");
		configuration.setProperty(AvailableSettings.ORDER_INSERTS, "true");
		configuration.setProperty(AvailableSettings.USE_NEW_ID_GENERATOR_MAPPINGS, "true");
		configuration.setProperty("jadira.usertype.autoRegisterUserTypes", "true");
		configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");

		// Add entity
		configuration.addAnnotatedClass(com.gitlab.zloster.World.class);

		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setUrl("jdbc:mysql://localhost:3306/hello_world");
		dataSource.setUser("benchmarkdbuser");
		dataSource.setPassword("benchmarkdbpass");

		Map<String, String> dataSourceConfiguration = new HashMap<>();
		dataSourceConfiguration.put("url", "jdbc:mysql://localhost:3306/hello_world");
		dataSourceConfiguration.put("user", "benchmarkdbuser");
		dataSourceConfiguration.put("password", "benchmarkdbpass");
		dataSourceConfiguration.put("driverClass", "com.mysql.jdbc.Driver");

		final DatasourceConnectionProviderImpl connectionProvider = new DatasourceConnectionProviderImpl();
		connectionProvider.setDataSource(dataSource);
		connectionProvider.configure(dataSourceConfiguration);

		final ServiceRegistry registry = new StandardServiceRegistryBuilder()
				.addService(ConnectionProvider.class, connectionProvider)
				.applySettings(configuration.getProperties()).build();

		return configuration.buildSessionFactory(registry);
	}
}