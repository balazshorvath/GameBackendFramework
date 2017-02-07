package hu.sovaroq.framework.service;

import hu.sovaroq.framework.bus.IEventBus;
import hu.sovaroq.framework.bus.SimpleEventBus;
import hu.sovaroq.framework.service.extension.Run;
import hu.sovaroq.framework.service.extension.Tick;
import hu.sovaroq.framework.service.extension.Ticker;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ServiceManager {
	public static final long WAIT_BEFORE_FORCE_SHUTDOWN = 10000;

	private final Logger log;
	private final Map<String, IService> services = new ConcurrentHashMap<>();
	private final ThreadPoolExecutor threadPool;

	private final IEventBus bus;
	private final Ticker ticker;

	private ManagerState state = ManagerState.IDLE;
	
	public ServiceManager(int corePoolSize, int maximumPoolSize, int tickerSize, Logger log){
		this.threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
		this.bus = new SimpleEventBus(corePoolSize, maximumPoolSize, 30, TimeUnit.SECONDS);
		this.ticker = new Ticker(tickerSize);
		this.log = log;
	}

	public void start(){
		ticker.start();
		state = ManagerState.STARTED;
	}
	public void stop(){
		state = ManagerState.STOPPING;
		ticker.stop();
		services.values().stream().forEach(IService::stop);

		threadPool.shutdown();

		long start = System.currentTimeMillis();

		while(!ticker.getState().equals(Ticker.TickerState.STOPPED)
				&& !threadPool.isShutdown()){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		state = ManagerState.STOPPED;
	}
	
	public <T extends IService> boolean manage(final Class<T> type, final T service){
		if(services.containsKey(service.getServiceId())){
			log.error("Service with id " + service.getServiceId() + " already exists.");
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
		
		services.put(service.getServiceId(), service);
		return true;
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
