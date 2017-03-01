package hu.sovaroq.framework.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.Logger;

import hu.sovaroq.framework.core.bus.IEventBus;
import hu.sovaroq.framework.core.bus.SimpleEventBus;
import hu.sovaroq.framework.core.configuration.ConfigurationCreator;
import hu.sovaroq.framework.core.logger.LogProvider;
import hu.sovaroq.framework.service.base.AbstractService;
import hu.sovaroq.framework.service.base.IService;
import hu.sovaroq.framework.service.base.Service;
import hu.sovaroq.framework.service.features.Run;
import hu.sovaroq.framework.service.features.Tick;
import hu.sovaroq.framework.service.features.Ticker;

public class ServiceManager {
	private final ConfigurationCreator configCreator = new ConfigurationCreator();
	
	private final Logger log = LogProvider.createLogger(this.getClass());
	private final Map<Class<? extends AbstractService>, AbstractService> services = new ConcurrentHashMap<>();
	private final ThreadPoolExecutor threadPool;

	private final IEventBus bus;
	private final Ticker ticker;

	private ManagerState state = ManagerState.IDLE;

	public ServiceManager(int corePoolSize, int maximumPoolSize, int tickerSize){
		this.threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
		this.bus = new SimpleEventBus(corePoolSize, maximumPoolSize, 30, TimeUnit.SECONDS);
		this.ticker = new Ticker(tickerSize);
	}
	public ServiceManager(IEventBus bus, int corePoolSize, int maximumPoolSize, int tickerSize){
		this.threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
		this.bus = bus;
		this.ticker = new Ticker(tickerSize);
	}
	public ServiceManager(IEventBus bus, Ticker ticker, ThreadPoolExecutor threadPool){
		this.threadPool = threadPool;
		this.bus = bus;
		this.ticker = ticker;
	}

	/**
	 * Does nothing atm.
	 */
	public void start(){
		log.info(">FrameworkController - start()");
		state = ManagerState.STARTED;
		log.info("<FrameworkController - start()");
	}
	/**
	 * Calls stop() on all the services.
	 * Shuts down it's thread pool.
	 */
	public void stop(){
		state = ManagerState.STOPPING;
        log.info("ServiceManager stopping.");
		ticker.stop();
		services.values().stream().forEach(IService::stop);

		threadPool.shutdown();

        try {
            if(!threadPool.awaitTermination(5000, TimeUnit.MILLISECONDS)) {
            	log.info("ThreadPool did not terminate, calling shutdownNow().");
                threadPool.shutdownNow();
            }
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
        log.info("ServiceManager successfully stopped.");
		state = ManagerState.STOPPED;
	}
	
	/**
	 * 1. Instantiates an instance out of the type.
	 * 2. Passes the bus to the service.
	 * 3. Loads and passes the context to the service.
	 * 
	 * @param type
	 * @return
	 */
	
	@SuppressWarnings("unchecked")
	public <T extends AbstractService> T manage(final Class<T> type){
		Service serviceAnnot = type.getAnnotation(Service.class);
		if(serviceAnnot == null){
			log.error("Class is not annotated with @Service '" + type.getName() + "'.");
			return null;
		}
		T service;
		try {
			service = type.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			log.error("Could not instantiate service '" + type.getName() + "'. " + e);
			return null;
		}
		service.setBus(bus);
		service.setConfig(configCreator.createConfig(serviceAnnot.configurationClass(), serviceAnnot.configurationFile()));
		
		if(services.containsKey(type)){
			log.error("Service with type " + type.getName() + " already exists.");
			return null;
		}
		
		for(Method m : type.getMethods()){
			Run run = m.getAnnotation(Run.class);
			Tick tick = m.getAnnotation(Tick.class);
			if(run != null && tick != null){
				log.warn("The service '" + type.getName() + "' has it's method '" + m.getName() + "' "
						+ "annotated with both Tick and Run, which is an invalide usage!");
			}
			if(run != null){
				registerRunnable(m, service);
			}else if(tick != null){
				registerTick(m, service, tick.value());
			}
		}
		
		bus.subscribe(service);
		
		services.put(type, service);
		return service;
	}
	
	
	
	public AbstractService getService(Class<? extends AbstractService> service) {
		return services.get(service);
	}

	public void restartService(Class<? extends AbstractService> service) {
		if(services.containsKey(service))
			services.get(service).restart();
	}
	public void stopService(Class<? extends AbstractService> service) {
		if(services.containsKey(service))
			services.get(service).stop();
	}

	private void registerTick(final Method m, final Object service, final long invocationTime) {
		ticker.addTickerCall(invocationTime, m, service);
	}

	private void registerRunnable(final Method m, final Object service){
		threadPool.execute(() -> {
			try {
				m.invoke(service);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// impossible
				e.printStackTrace();
			}
		});
	}
	


	public enum ManagerState{
		IDLE,
		STARTED,
		STOPPING,
		STOPPED
	}
}
