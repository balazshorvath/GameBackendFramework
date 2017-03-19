package hu.sovaroq.core.database.service;

import hu.sovaroq.core.database.repository.HibernateRepository;
import hu.sovaroq.core.database.repository.Repository;
import hu.sovaroq.framework.logger.LogProvider;
import hu.sovaroq.framework.service.AbstractService;
import hu.sovaroq.framework.service.Service;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service(configurationClass = HibernateDatabaseService.DatabaseConfig.class, configurationFile = "database/HibernateDatabaseService.properties")
public class HibernateDatabaseService extends AbstractService<HibernateDatabaseService.DatabaseConfig>
        implements IHibernateSessionProvider {

    private static final Logger log = LogProvider.createLogger(HibernateDatabaseService.class);
    private EntityManagerFactory entityManagerFactory;
    private EntityManager em;
    private DatabaseConfig config;
    private Map<Class<?>, HibernateRepository<?>> repositories = new HashMap<>();

    public HibernateDatabaseService() {
        super();
    }

    @Override
    public void start(DatabaseConfig config) {
        log.info(">HibernateDatabaseService - start()");
        super.start(config);
        initEntityManager();
        log.info("<HibernateDatabaseService - start()");

    }

    @Override
    public void stop() {
        if (em != null && em.isOpen()) {
            em.close();
            log.info(">HibernateDatabaseService - stop()");
            post(new IDatabaseServiceEvents.DatabaseServiceStopped());
        }
    }

    @Override
    public void restart() {
        log.info(">HibernateDatabaseService - restart()");
        stop();
        initEntityManager();
        post(new IDatabaseServiceEvents.DatabaseServiceRestarted());
        log.info("<HibernateDatabaseService - restart()");
    }

    @Override
    public void setConfig(HibernateDatabaseService.DatabaseConfig config) {
        this.config = config;
    }

    public HibernateRepository<?> getRepository(Class<?> clazz) {
        return repositories.get(clazz);
    }

    @Override
    public EntityManager getEntityManager() {
        log.info(">HibernateDatabaseService - getEntityManager()");
        return em;
    }

    @Override
    public String getStatusDescription() {
        return "HibernateDatabaseService is currently " + ((em == null || !em.isOpen()) ? "connected" : "not connected")
                + ",\nwith context " + this.config + ".";
    }

    @Override
    public Double getWorkload() {
        return 0.0;
    }

    @SuppressWarnings("unchecked")
    private void initEntityManager() {
        log.info(">HibernateDatabaseService - initEntityManager()");
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
                    Constructor<? extends HibernateRepository<?>> c = (Constructor<? extends HibernateRepository<?>>) repository
                            .getConstructor(IHibernateSessionProvider.class);

                    HibernateRepository<?> repo = c.newInstance(this);

                    this.repositories.put(repo.getEntityType(), repo);
                } catch (Exception e) {
                    log.error("An exception was thrown while working on repositories.", e);
                }
            }

        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        log.info("<HibernateDatabaseService - initSessionFactory()");
    }

    public CriteriaBuilder getCriteriaBuilder() {
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
