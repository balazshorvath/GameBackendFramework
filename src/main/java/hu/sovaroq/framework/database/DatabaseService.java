package hu.sovaroq.framework.database;

import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import hu.sovaroq.framework.bus.IEventBus;
import hu.sovaroq.framework.configuration.annotation.ConfigFileParser;
import hu.sovaroq.framework.logger.LogProvider;
import hu.sovaroq.framework.service.IController;
import hu.sovaroq.framework.service.AbstractService;
import hu.sovaroq.game.core.base.BuildingBase;
import hu.sovaroq.game.core.base.CommanderBase;
import hu.sovaroq.game.core.base.UnitBase;
import hu.sovaroq.game.core.config.DefaultFileParser;

public class DatabaseService extends AbstractService<DatabaseService.DatabaseConfig>{

	private static SessionFactory sessionFactory = buildSessionFactory();
	
	private static final Logger log = LogProvider.createLogger(DatabaseService.class);

	private static SessionFactory buildSessionFactory() {
		try {
			if (sessionFactory == null) {
				Configuration configuration = new Configuration()
						.configure(DatabaseService.class.getResource("/hibernate.cfg.xml"));
				StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
				serviceRegistryBuilder.applySettings(configuration.getProperties());
				ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
				// That was missing
				configuration.addAnnotatedClass(UnitBase.class);
				configuration.addAnnotatedClass(BuildingBase.class);
				configuration.addAnnotatedClass(CommanderBase.class);
				sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			}
			return sessionFactory;
		} catch (Throwable ex) {
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
	
	public DatabaseService(){
		super();
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void shutdown() {
		getSessionFactory().close();
	}

	@Override
	public void restart() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setConfig(DatabaseService.DatabaseConfig config) {
		// TODO Auto-generated method stub
		
	}

	public class DatabaseConfig{
		
	}
	
	public static void main(String[] args){
		Session session = sessionFactory.openSession();
		
		UnitBase unit = new UnitBase();
		unit.setBaseAttackDamage(1);
		unit.setBaseHP(5);
		unit.setBaseMovementSpeed(1);
		unit.setDescription("test unit description");
		unit.setName("test name unit");
		
		session.save(unit);
		
		UnitBase unit2 = new UnitBase();
		unit2.setBaseAttackDamage(1);
		unit2.setBaseHP(5);
		unit2.setBaseMovementSpeed(1);
		unit2.setDescription("test unit description2");
		unit2.setName("test name unit2");
		
		session.save(unit2);
		
		BuildingBase building = new BuildingBase();
		Set<UnitBase> units = new HashSet<UnitBase>();
		units.add(unit);
		units.add(unit2);
		building.setAvailableUnits(units);
		building.setBaseAttackDamage(10);
		building.setBaseHP(30);
		building.setDescription("building test description");
		building.setName("name");
		
		Long i = (Long) session.save(building);
		
		log.info("Building saved, ID: " + i.toString());
		
		
		BuildingBase testBuilding = session.get(BuildingBase.class, i);
		
		log.debug("building: " + testBuilding);
		
		session.close();
	}

	@Override
	public String getStatusDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double getWorkload() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void start(DatabaseConfig config) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}
	
}
