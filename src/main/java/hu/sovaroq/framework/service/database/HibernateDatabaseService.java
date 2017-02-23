package hu.sovaroq.framework.service.database;

import hu.sovaroq.framework.core.logger.LogProvider;
import hu.sovaroq.framework.data.user.User;
import hu.sovaroq.framework.database.HibernateRepository;
import hu.sovaroq.framework.database.Repository;
import hu.sovaroq.framework.service.base.AbstractService;
import hu.sovaroq.framework.service.base.Service;
import hu.sovaroq.framework.service.chat.ChatMessage;
import hu.sovaroq.framework.service.chat.Conversation;
import hu.sovaroq.game.core.data.BuildingBase;
import hu.sovaroq.game.core.data.CommanderBase;
import hu.sovaroq.game.core.data.UnitBase;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service(
		configurationClass = HibernateDatabaseService.DatabaseConfig.class,
		configurationFile = "database/HibernateDatabaseService.properties"
)
public class HibernateDatabaseService extends AbstractService<HibernateDatabaseService.DatabaseConfig> implements IHibernateSessionProvider {
	private SessionFactory sessionFactory;
	private DatabaseConfig config;

	private Map<Class<?>, HibernateRepository> repositories = new HashMap<>();

	private static final Logger log = LogProvider.createLogger(HibernateDatabaseService.class);

	public HibernateDatabaseService() {
		super();
	}

	@Override
	public void start(DatabaseConfig config) {
		super.start(config);
		initSessionFactory();
	}

	@Override
	public void stop() {
		if(sessionFactory != null && !sessionFactory.isClosed()) {
			sessionFactory.close();
			sessionFactory = null;
			post(new IDatabaseServiceEvents.DatabaseServiceStopped());
		}
	}
	@Override
	public void restart() {
		stop();
		initSessionFactory();
		post(new IDatabaseServiceEvents.DatabaseServiceRestarted());
	}

	@Override
	public void setConfig(HibernateDatabaseService.DatabaseConfig config) {
		this.config = config;
	}

	public HibernateRepository getRepository(Class<?> clazz) {
		return repositories.get(clazz);
	}

	@Override
	public Session openSession() {
		if(sessionFactory != null && !sessionFactory.isClosed()){
			return sessionFactory.openSession();
		}
		return null;
	}

	@Override
	public String getStatusDescription() {
		return "HibernateDatabaseService is currently "
				+ ((sessionFactory == null || sessionFactory.isClosed()) ? "connected" : "not connected")
				+ ",\nwith context " + this.config + ".";
	}

	@Override
	public Double getWorkload() {
		return 0.0;
	}

	@SuppressWarnings("unchecked")
	private void initSessionFactory() {
		try {
			if (sessionFactory == null) {
				Configuration configuration = new Configuration()
						.configure(HibernateDatabaseService.class.getResource(config.getHibernateConfig()));
				StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
				serviceRegistryBuilder.applySettings(configuration.getProperties());
				ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
				// That was missing
				Reflections reflections = new Reflections();
				Set<Class<?>> repositories = reflections.getTypesAnnotatedWith(Repository.class);

				for (Class<?> repository : repositories) {
					if(!HibernateRepository.class.isAssignableFrom(repository))
						continue;
					try {
						Constructor<? extends HibernateRepository> c = (Constructor<? extends HibernateRepository>) repository.getConstructor(IHibernateSessionProvider.class);

						HibernateRepository repo = c.newInstance(this);

						configuration.addAnnotatedClass(repo.getEntityType());
						this.repositories.put(repo.getEntityType(), repo);
					}catch (Exception e){
						log.error("An exception was thrown while working on repositories.", e);
					}
				}


				sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			} else {
				sessionFactory.close();

			}
		} catch (Throwable ex) {
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}


	@Data
	@ToString
	@NoArgsConstructor
	public static class DatabaseConfig {
		// "/hibernate.cfg.xml"
		String hibernateConfig;
	}

}
