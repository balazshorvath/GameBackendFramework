package hu.sovaroq.framework.service.extension;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.PriorityQueue;

import org.apache.logging.log4j.Logger;

import hu.sovaroq.framework.logger.LogProvider;

public class Ticker {
	protected final PriorityQueue<TickCall> calls;
	
	protected Logger log;
	protected boolean running = false;

	protected TickerState state = TickerState.NOT_STARTED;

	public Ticker(int capacity, Logger log){
		calls = new PriorityQueue<>(capacity);
		this.log = log;
	}
	
	public Ticker(int capacity){
		calls = new PriorityQueue<>(capacity);
		this.log = LogProvider.createLogger(this.getClass());
	}
	
	
	public Runnable start() {
		if(running){
			return null;
		}
		running = true;
		state = TickerState.IDLE;
		return () -> {
			state = TickerState.STARTED;
			TickCall call;
			long currentTime;
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
				if(call.nextCall <= currentTime){
					try {
						call = calls.poll();
						call.m.invoke(call.o);
						
						call.nextCall = currentTime + call.callMs;
						calls.add(call);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						// There's no way this will happen, still that call has to be removed
						// Simply don't put it back
						log.warn("Could not invoke ticker call.");
					}
				}else {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						running = false;
						state = TickerState.STOPPING;
					}
				}
				
			}
			state = TickerState.STOPPED;
			calls.clear();
		};
	}
	
	public void stop(){
		state = TickerState.STOPPING;
		running = false;
	}
	
	public void addTickerCall(final long callMs, final Method m, final Object o){
		calls.add(new TickCall(callMs, m, o));
	}

	public TickerState getState() {
		return state;
	}

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
