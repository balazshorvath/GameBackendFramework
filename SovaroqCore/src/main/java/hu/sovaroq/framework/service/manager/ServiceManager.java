package hu.sovaroq.framework.service.manager;

import hu.sovaroq.framework.eventing.bus.IEventBus;
import hu.sovaroq.framework.eventing.bus.SimpleEventBus;
import hu.sovaroq.framework.service.AbstractService;
import hu.sovaroq.framework.service.IService;
import hu.sovaroq.framework.service.Service;
import hu.sovaroq.framework.service.configuration.ConfigurationCreator;
import hu.sovaroq.framework.logger.LogProvider;
import hu.sovaroq.framework.service.features.AutoSetService;
import hu.sovaroq.framework.service.features.Run;
import hu.sovaroq.framework.service.features.Tick;
import hu.sovaroq.framework.service.features.Ticker;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
     * Calls {@link #manageService(AbstractService)} on the previously added services.
     * Starts the bus.
	 */
	public void start(){
		log.info(">ServiceManager - start()");
		if(state != ManagerState.STARTED){
            services.values().forEach(this::manageService);
            bus.start();
            state = ManagerState.STARTED;
        }else {
            log.warn("Already started...");
        }
		log.info("<ServiceManager - start()");
	}
	/**
	 * Calls stop() on all the services.
	 * Shuts down it's thread pool.
	 */
	public void stop(){
		state = ManagerState.STOPPING;
        log.info("ServiceManager stopping.");
		ticker.stop();
		services.values().forEach(IService::stop);

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
     * Adds a new {@link AbstractService} to the managed services.
     *
     * Creates a new instance of the type.
     * Checks, if the class is properly annotated, then adds it to the managed services list.
     * If the {@link ServiceManager} is in {@link ManagerState#STARTED} state,
     * {@link #manageService(AbstractService)} is also called on the service.
     *
	 * @param type
	 * @return
	 */
	
	@SuppressWarnings("unchecked")
	public <T extends AbstractService> T manage(final Class<T> type){
		if(services.containsKey(type)){
			log.error("Service with type " + type.getName() + " already exists.");
			return null;
		}

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
		if(state == ManagerState.STARTED){
            log.info("Manager is started. Starting it immediately: " + type);
            manageService(service);
        }else {
            log.info("Manager is not yet started. Service instance is stored: " + type);

        }
		services.put(type, service);
		return service;
	}

    /**
     * Calls in this order:
     *
     * {@link #autoSetFields(AbstractService)}
     * {@link #startMethodFeatures(AbstractService)}
     * {@link #startService(AbstractService)}
     *
     * @param service
     * @param <T>
     */
    private <T extends AbstractService> void manageService(T service){
        autoSetFields(service);
        startMethodFeatures(service);
        startService(service);
    }

    /**
     * The first step of the initialization of an {@link AbstractService}.
     *
     * Sets the fields annotated with @AutoSetService, if:
     *      - The type of the field is an an {@link AbstractService},
     *      - The type of the field is already managed by this service manager.
     *
     * @param service
     * @param <T>
     */
    private <T extends AbstractService> void autoSetFields(T service){
        log.info("Auto setting fields on service: " + service.getClass().getName());
        for (Field field : service.getClass().getDeclaredFields()) {
            if(field.getAnnotation(AutoSetService.class) == null){
                continue;
            }
            try {
                if(AbstractService.class.isAssignableFrom(field.getType())) {
                    field.setAccessible(true);
                    field.set(service, getService((Class<? extends AbstractService>) field.getType()));
                }
            } catch (Exception e) {
                log.error("Could not set service '" + field.getType() + "' to field '" + field.getName() + "' in '" + service.getClass() + "'");
            }
        }
    }

    /**
     * The second step of the initialization of an {@link AbstractService}.
     *
     * Sets the bus,
     * calls the {@link AbstractService#start(Object)} method with the proper config,
     * subscribes the service to the event bus
     *
     * @param service
     * @param <T>
     */
    private <T extends AbstractService> void startService(T service){
        log.info("Starting service: " + service.getClass().getName());
        Service serviceAnnot = service.getClass().getAnnotation(Service.class);
        service.setBus(bus);
        service.start(configCreator.createConfig(serviceAnnot.configurationClass(), serviceAnnot.configurationFile()));

        bus.subscribe(service);
    }

    /**
     * The third step of the initialization of an {@link AbstractService}.
     *
     * Registers the service methods annotated with {@link Run}, or {@link Tick} to the thread pool, or the ticker.
     *
     *
     * @param service
     * @param <T>
     */
    private <T extends AbstractService> void startMethodFeatures(T service) {
        log.info("Starting method features on service: " + service.getClass().getName());
        for(Method m : service.getClass().getMethods()) {
            Run run = m.getAnnotation(Run.class);
            Tick tick = m.getAnnotation(Tick.class);
            if (run != null && tick != null) {
                log.warn("The service '" + service.getClass().getName() + "' has it's method '" + m.getName() + "' "
                        + "annotated with both Tick and Run, which is an invalid usage!");
            }
            if (run != null) {
                registerRunnable(m, service);
            } else if (tick != null) {
                registerTick(m, service, tick.value());
            }
        }
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

	public IEventBus getBus() {
		return bus;
	}

	public enum ManagerState{
		IDLE,
		STARTED,
		STOPPING,
		STOPPED
	}
}
