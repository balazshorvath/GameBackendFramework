package hu.sovaroq.framework.database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import hu.sovaroq.game.core.base.Unit;

public class DatabaseService {

	private static SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		try {
			if (sessionFactory == null) {
				Configuration configuration = new Configuration()
						.configure(DatabaseService.class.getResource("/hibernate.cfg.xml"));
				StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
				serviceRegistryBuilder.applySettings(configuration.getProperties());
				ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
				sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			}
			return sessionFactory;
		} catch (Throwable ex) {
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void shutdown() {
		getSessionFactory().close();
	}
	
	public static void main(String[] args){
		System.out.println("test");
		
		Session session = DatabaseService.getSessionFactory().openSession();
		session.beginTransaction();
		
		Unit unit = new Unit();
		unit.setDescription("test");
		unit.setName("testname");
		
		session.save(unit);
		session.getTransaction().commit();
		DatabaseService.shutdown();
		
	}

}
