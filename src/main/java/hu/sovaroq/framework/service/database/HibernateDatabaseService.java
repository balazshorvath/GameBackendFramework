package hu.sovaroq.framework.service.database;

import hu.sovaroq.framework.core.logger.LogProvider;
import hu.sovaroq.framework.data.user.User;
import hu.sovaroq.framework.database.HibernateRepository;
import hu.sovaroq.framework.service.base.AbstractService;
import hu.sovaroq.framework.service.base.Service;
import hu.sovaroq.framework.service.chat.ChatMessage;
import hu.sovaroq.framework.service.chat.Conversation;
import hu.sovaroq.game.core.data.BuildingBase;
import hu.sovaroq.game.core.data.CommanderBase;
import hu.sovaroq.game.core.data.UnitBase;
import lombok.Data;
import lombok.ToString;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.HashMap;
import java.util.Map;

@Service(
		configNeedsRestart = true,
		configurationClass = HibernateDatabaseService.DatabaseConfig.class,
		configurationFile = "HibernateDatabaseService.properties"
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
	public void restart() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setConfig(HibernateDatabaseService.DatabaseConfig config) {
		this.config = config;

	}

	public <T> HibernateRepository<T> getRepository(Class<T> clazz) {
		if (repositories.containsKey(clazz)) {
			return repositories.get(clazz);
		}else{
			return null;
		}
	}

	@Override
	public Session openSession() {
		if(sessionFactory != null && !sessionFactory.isClosed()){
			return sessionFactory.openSession();
		}
		return null;
	}

	@Data
	@ToString
	public class DatabaseConfig {
		// "/hibernate.cfg.xml"
		String hibernateConfig;
	}

	@Override
	public String getStatusDescription() {
		return "HibernateDatabaseService is currently "
				+ ((sessionFactory == null || sessionFactory.isClosed()) ? "connected" : "not connected")
				+ ",\nwith config " + this.config + ".";
	}

	@Override
	public Double getWorkload() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void start(DatabaseConfig config) {
		this.config = config;
		initSessionFactory();
	}

	@Override
	public void stop() {
		sessionFactory.close();
	}

	private void initSessionFactory() {
		try {
			if (sessionFactory == null) {
				Configuration configuration = new Configuration()
						.configure(HibernateDatabaseService.class.getResource(config.getHibernateConfig()));
				StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
				serviceRegistryBuilder.applySettings(configuration.getProperties());
				ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
				// That was missing
				// TODO this should be in config somehow. Probably hibernate xml.
				configuration.addAnnotatedClass(UnitBase.class);
				configuration.addAnnotatedClass(BuildingBase.class);
				configuration.addAnnotatedClass(CommanderBase.class);
				configuration.addAnnotatedClass(ChatMessage.class);
				configuration.addAnnotatedClass(Conversation.class);
				configuration.addAnnotatedClass(User.class);

				sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			} else {
				sessionFactory.close();

			}
		} catch (Throwable ex) {
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

}
