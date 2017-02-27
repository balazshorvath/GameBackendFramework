package hu.sovaroq.framework.service.database;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

import hu.sovaroq.framework.core.logger.LogProvider;
import hu.sovaroq.framework.database.HibernateRepository;
import hu.sovaroq.framework.database.Repository;
import hu.sovaroq.framework.service.base.AbstractService;
import hu.sovaroq.framework.service.base.Service;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Service(configurationClass = HibernateDatabaseService.DatabaseConfig.class, configurationFile = "database/HibernateDatabaseService.properties")
public class HibernateDatabaseService extends AbstractService<HibernateDatabaseService.DatabaseConfig>
		implements IHibernateSessionProvider {

	private EntityManagerFactory entityManagerFactory;
	private EntityManager em;

	private DatabaseConfig config;

	private Map<Class<?>, HibernateRepository> repositories = new HashMap<>();

	private static final Logger log = LogProvider.createLogger(HibernateDatabaseService.class);

	public HibernateDatabaseService() {
		super();
	}

	@Override
	public void start(DatabaseConfig config) {
		super.start(config);
		initEntityManager();
	}

	@Override
	public void stop() {
		if (em != null && em.isOpen()) {
			em.close();
			post(new IDatabaseServiceEvents.DatabaseServiceStopped());
		}
	}

	@Override
	public void restart() {
		stop();
		initEntityManager();
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
	public EntityManager getEntityManager() {
			return em;
	}

	@Override
	public String getStatusDescription() {
		return "HibernateDatabaseService is currently "
				+ ((em == null || !em.isOpen()) ? "connected" : "not connected")
				+ ",\nwith context " + this.config + ".";
	}

	@Override
	public Double getWorkload() {
		return 0.0;
	}

	@SuppressWarnings("unchecked")
	private void initEntityManager() {
		try {
			entityManagerFactory = Persistence.createEntityManagerFactory("HsqldbWithTDD");
			em = entityManagerFactory.createEntityManager();
						
			// That was missing
			Reflections reflections = new Reflections();
			Set<Class<?>> repositories = reflections.getTypesAnnotatedWith(Repository.class);

			for (Class<?> repository : repositories) {
				if (!HibernateRepository.class.isAssignableFrom(repository))
					continue;
				try {
					Constructor<? extends HibernateRepository> c = (Constructor<? extends HibernateRepository>) repository
							.getConstructor(IHibernateSessionProvider.class);

					HibernateRepository repo = c.newInstance(this);

					this.repositories.put(repo.getEntityType(), repo);
				} catch (Exception e) {
					log.error("An exception was thrown while working on repositories.", e);
				}
			}

		} catch (Throwable ex) {
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
	
	public CriteriaBuilder getCriteriaBuilder(){		
		return entityManagerFactory.getCriteriaBuilder();
	}

	@Data
	@ToString
	@NoArgsConstructor
	public static class DatabaseConfig {
		// "/hibernate.cfg.xml"
		String hibernateConfig;
	}

}
