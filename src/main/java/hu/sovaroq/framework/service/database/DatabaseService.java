package hu.sovaroq.framework.service.database;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import hu.sovaroq.framework.core.logger.LogProvider;
import hu.sovaroq.framework.data.user.User;
import hu.sovaroq.framework.service.base.AbstractService;
import hu.sovaroq.framework.service.chat.ChatMessage;
import hu.sovaroq.framework.service.chat.Conversation;
import hu.sovaroq.game.core.data.BuildingBase;
import hu.sovaroq.game.core.data.CommanderBase;
import hu.sovaroq.game.core.data.UnitBase;

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
				configuration.addAnnotatedClass(ChatMessage.class);
				configuration.addAnnotatedClass(Conversation.class);
				configuration.addAnnotatedClass(User.class);
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
		List<User> users = new ArrayList<>();
		List<ChatMessage> messages = new ArrayList<>();
		List<Conversation> conversations = new ArrayList<>();
		SecureRandom random = new SecureRandom();
		
		Random randomGenerator = new Random();
		
		Transaction transaction = session.beginTransaction();
		
		long startLoading = System.currentTimeMillis();
		
		for(int i=0; i <= 10; i++){
			User user = new User();
			user.setLogin("User" + i);
			user.setPassword("pass");
			session.save(user);
			users.add(user);
		}
		
		for(int i = 0; i <= 5; i++){
			Conversation conversation = new Conversation();
			conversation.setName("TestConversation" + i);
			Set<User> convUsers = new HashSet<>();
			for(int j=0; j <= i % 2; j++){
				convUsers.add(users.get(randomGenerator.nextInt(users.size())));
			}
			conversation.setParticipants(convUsers);			
			session.save(conversation);
			conversations.add(conversation);
		}
		
		for(int i = 0; i <= 100000; i++){
			ChatMessage message = new ChatMessage();
			message.setConversation(conversations.get(randomGenerator.nextInt(conversations.size())));
			message.setMessage(getRandomString(random));
			message.setUser(users.get(randomGenerator.nextInt(users.size())));
			message.setTimestamp(random.nextLong());
			session.save(message);
			messages.add(message);
		}
		
		transaction.commit();
		
		long stoploading = System.currentTimeMillis();
		
		System.out.println("Elapsed time from start until write ready is " + (stoploading - startLoading) + " ms.");

		
		
//		Long i = (Long) session.save(building);
		
//		log.info("Building saved, ID: " + i.toString());
		
		
//		BuildingBase testBuilding = session.get(BuildingBase.class, i);
		
//		log.debug("building: " + testBuilding);
		
		session.close();
		
		sessionFactory.close();
	}
	
	private static String getRandomString(SecureRandom random){
		return new BigInteger(130, random).toString(32);
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
