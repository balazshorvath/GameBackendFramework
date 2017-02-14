package hu.sovaroq.framework.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.enterprise.inject.New;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hu.sovaroq.framework.features.bus.IEventBus;
import hu.sovaroq.framework.features.bus.SimpleEventBus;
import hu.sovaroq.framework.features.configuration.ConfigurationCreator;
import hu.sovaroq.framework.features.logger.LogProvider;
import hu.sovaroq.framework.service.base.AbstractService;
import hu.sovaroq.framework.service.base.IService;
import hu.sovaroq.framework.service.base.Service;
import hu.sovaroq.framework.service.features.Run;
import hu.sovaroq.framework.service.features.Tick;
import hu.sovaroq.framework.service.features.Ticker;

public class ServiceManager {
	public static final long WAIT_BEFORE_FORCE_SHUTDOWN = 10000;

	private final ConfigurationCreator configCreator = new ConfigurationCreator();
	
	private final Logger log;
	private final Map<Class<? extends AbstractService>, AbstractService> services = new ConcurrentHashMap<>();
	private final ThreadPoolExecutor threadPool;

	private final IEventBus bus;
	private final Ticker ticker;

	private ManagerState state = ManagerState.IDLE;

	public ServiceManager(int corePoolSize, int maximumPoolSize, int tickerSize, Logger log){
		this.threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
		this.bus = new SimpleEventBus(corePoolSize, maximumPoolSize, 30, TimeUnit.SECONDS);
		this.ticker = new Ticker(tickerSize, log);
		this.log = log;
	}
	public ServiceManager(IEventBus bus, int corePoolSize, int maximumPoolSize, int tickerSize, Logger log){
		this.threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
		this.bus = bus;
		this.ticker = new Ticker(tickerSize, log);
		this.log = log;
	}
	public ServiceManager(IEventBus bus, Ticker ticker, ThreadPoolExecutor threadPool, Logger log){
		this.threadPool = threadPool;
		this.bus = bus;
		this.ticker = ticker;
		this.log = log;
	}

	/**
	 * Does nothing atm.
	 */
	public void start(){
		state = ManagerState.STARTED;
	}
	
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
	
	public <T extends AbstractService> boolean manage(final Class<T> type){
		T service;
		try {
			service = type.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			log.error("Could not instanciate service '" + type.getName() + "'. " + e.getMessage());
			return false;
		}
		Service serviceAnnot = type.getAnnotation(Service.class);
		service.setBus(bus);
		service.setConfig(configCreator.createConfig(type, serviceAnnot.configurationFile()));
		
		if(services.containsKey(type)){
			log.error("Service with type " + type.getName() + " already exists.");
			return false;
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
		
		bus.subscribe(type, service);
		
		services.put(type, service);
		return true;
	}
	
	
	
	public Map<Class<? extends AbstractService>, AbstractService> getServices() {
		return services;
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
