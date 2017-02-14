package hu.sovaroq.framework.service.features;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.PriorityQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.Logger;

import hu.sovaroq.framework.core.logger.LogProvider;
import lombok.ToString;

public class TickerOld {
	protected final PriorityQueue<TickCall> calls;
	protected final ThreadPoolExecutor threadPool;
	
	protected Logger log;
	protected boolean running = false;

	protected TickerState state = TickerState.NOT_STARTED;

	public TickerOld(int capacity, Logger log){
		this.calls = new PriorityQueue<>(capacity);
		this.threadPool = new ThreadPoolExecutor(capacity, capacity * 2, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
		this.log = log;
	}
	
	public TickerOld(int capacity){
		this.calls = new PriorityQueue<>(capacity);
		this.threadPool = new ThreadPoolExecutor(capacity, capacity * 2, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
		this.log = LogProvider.createLogger(this.getClass());
	}
	
	
	public Runnable start() {
		log.info("Setting up Ticker");
		if(running){
			return null;
		}
		running = true;
		state = TickerState.IDLE;
		return () -> {
			state = TickerState.STARTED;
			TickCall call;
			long currentTime;
			log.info("Ticker thread has started");
			while(running){
				if((call = calls.peek()) == null){
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						running = false;
						state = TickerState.STOPPING;
						continue;
					}
					continue;
				}
				currentTime = System.currentTimeMillis();
				// To avoid the latency in calling
				if(call.nextCall <= (currentTime)){
					call = calls.poll();
					final TickCall finalCall = call;
					this.threadPool.execute(() -> {
						try {
							finalCall.m.invoke(finalCall.o);
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							// There's no way this will happen, still that call has to be removed
							// Simply don't put it back
							log.warn("Could not invoke ticker call.");
						}
					});
					
					call.nextCall = currentTime + call.callMs;
					calls.add(call);
					
				}else {
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						running = false;
						state = TickerState.STOPPING;
					}
				}
				
			}
			log.info("Ticker thread has stopped.");
		};
	}
	
	public void stop(){
		state = TickerState.STOPPING;
		running = false;
		
		threadPool.shutdown();

        try {
            if(!threadPool.awaitTermination(5000, TimeUnit.MILLISECONDS)) {
            	log.info("ThreadPool did not terminate, calling shutdownNow().");
                threadPool.shutdownNow();
            }
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
        
		calls.clear();
	}
	
	public void addTickerCall(final long callMs, final Method m, final Object o){
		calls.add(new TickCall(callMs, m, o));
	}

	public TickerState getState() {
		return state;
	}

	@ToString
	protected class TickCall implements Comparable<TickCall>{
		public long nextCall;
		public final long callMs;
		public final Method m;
		public final Object o;
		
		public TickCall(final long callMs, final Method m, final Object o){
			this.callMs = callMs;
			this.m = m;
			this.o = o;
			nextCall = System.currentTimeMillis() + callMs;
		}

		@Override
		public int compareTo(TickCall o) {
			return (int) (this.nextCall - o.nextCall);
		}
	}
	public enum TickerState {
		NOT_STARTED,
		IDLE,
		STARTED,
		STOPPING,
		STOPPED
	}

}
