package hu.sovaroq.framework.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.Logger;

import hu.sovaroq.framework.bus.IEventBus;
import hu.sovaroq.framework.bus.SimpleEventBus;
import hu.sovaroq.framework.service.extension.Run;
import hu.sovaroq.framework.service.extension.Tick;
import hu.sovaroq.framework.service.extension.Ticker;

public class ServiceManager {
	private final Logger log;
	private final Map<String, IService> services = new ConcurrentHashMap<>();
	
	private final Queue<Runnable> runnables = new ConcurrentLinkedQueue<>();

	private final IEventBus bus;
	private final Ticker ticker = new Ticker();
	
	public ServiceManager(int corePoolSize, int maximumPoolSize, int tickerSize, Logger log){
		bus = new SimpleEventBus(corePoolSize, maximumPoolSize, 30, TimeUnit.SECONDS);
		this.log = log;
	}
	public ServiceManager(IEventBus bus, int tickerSize, Logger log){
		this.bus = bus;
		this.log = log;
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
				startRunnable(m, service);
			}else if(tick != null){
				startTick(m, service);
			}
		}
		
		bus.subscribe(type, service);
		
		services.put(service.getServiceId(), service);
		return true;
	}
	
	
	private void startTick(Method m, Object service) {
		
	}

	private void startRunnable(final Method m, final Object service){
		Thread t = new Thread(() -> {
			try {
				m.invoke(service);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		t.start();
		runnables.add(t);
	}
}
