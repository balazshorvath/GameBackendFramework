package hu.sovaroq.framework.service.features;

import hu.sovaroq.framework.logger.LogProvider;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Ticker {
	protected final ScheduledThreadPoolExecutor threadPool;
	
	protected final Logger log;

	public Ticker(int capacity, Logger log){
		this.threadPool = new ScheduledThreadPoolExecutor(capacity);
		this.log = log;
	}
	
	public Ticker(int capacity){
		this.threadPool = new ScheduledThreadPoolExecutor(capacity);
		this.log = LogProvider.createLogger(this.getClass());
	}
	
	public void stop(){
		threadPool.shutdown();
		try {
			if(!threadPool.awaitTermination(5000, TimeUnit.SECONDS)){
				log.info("Thread pool did not stop after 5 seconds. Calling .shoutdownNow().");
				threadPool.shutdownNow();
			}
		} catch (InterruptedException ignored) {
			log.info("awaitTermination() was interrupted. Calling .shoutdownNow().");
			threadPool.shutdownNow();
		}
	}
	
	public void addTickerCall(final long callMs, final Method m, final Object o){
		log.info("New ticker method: " + m.getName() + " on object " + o + " with " + callMs + " ms delay.");
		threadPool.scheduleAtFixedRate(createRunnable(m, o), 0, callMs, TimeUnit.MILLISECONDS); 
	}

	private Runnable createRunnable(final Method m, final Object o) {
		return () -> {
			try {
				m.invoke(o);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
	}

}
