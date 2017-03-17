package hu.sovaroq.framework.service.ticker;

import hu.sovaroq.framework.service.AbstractService;
import hu.sovaroq.framework.service.features.Tick;
import hu.sovaroq.framework.service.features.Ticker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TickerTest {
	private final Logger log = LogManager.getLogger("console");
	private Ticker ticker;
	private Method m2;
	private Method m3;
	
	private List<TickingServiceExample> testServices = new ArrayList<>();
	
	@Before
	public void setup() throws NoSuchMethodException, SecurityException{
		ticker = new Ticker(30, log);

		m2 = TickingServiceExample.class.getMethod("tick50");
		m3 = TickingServiceExample.class.getMethod("tick100");
	}
	@Test
	public void testTiming() throws InterruptedException{
		for (int i = 0; i < 5; i++) {
			TickingServiceExample example = new TickingServiceExample();

			ticker.addTickerCall(m2.getAnnotation(Tick.class).value(), m2, example);
			ticker.addTickerCall(m3.getAnnotation(Tick.class).value(), m3, example);
			
			testServices.add(example);
		}
		long finishAt = System.currentTimeMillis() + 5000;
		Runtime rt = Runtime.getRuntime();
		
		while(finishAt >= System.currentTimeMillis()){
		    long usedMB = (rt.totalMemory() - rt.freeMemory()) / 1024 / 1024;
		    log.info("Memory usage: " + usedMB + " MB.");
			Thread.sleep(30);
		}
		
		ticker.stop();
		
		double call50Avg = 0L;
		int call50Times;
		
		double call100Avg = 0L;
		int call100Times;
		
		for(TickingServiceExample e : testServices){
			Queue<Long> calls;
			calls = e.methodCallTimestamps.get("tick50");
			// -1, because the first call doesn't count
			call50Times = calls.size() - 1;
			// Collect data
			if(call50Times > 1){
				long previous = calls.poll();
				while(calls.peek() != null){
					Assert.assertTrue(previous < calls.peek());
					call50Avg += calls.peek() - previous;
					previous = calls.poll();
				}
				call50Avg /= (double)call50Times;
				System.out.println("50 ms method called " + call50Times + " times with an avg of " + call50Avg + " ms time difference.");
				Assert.assertTrue(call50Avg < 55 && call50Avg > 49);
			}else {
				Assert.fail();
			}

			calls = e.methodCallTimestamps.get("tick100");
			// -1, because the first call doesn't count
			call100Times = calls.size() - 1;
			// Collect data
			if(call100Times > 1){
				long previous = calls.poll();
				while(calls.peek() != null){
					Assert.assertTrue(previous < calls.peek());
					call100Avg += calls.peek() - previous;
					previous = calls.poll();
				}
				call100Avg /= (double)call100Times;
				System.out.println("100 ms method called " + call100Times + " times with an avg of " + call100Avg + " ms time difference.");
				Assert.assertTrue(call100Avg < 105 && call100Avg > 99);
			}else {
				Assert.fail();
			}
		}
	}
	
	public class TickingServiceExample extends AbstractService<Object>{
		public final Map<String, Queue<Long>> methodCallTimestamps = new ConcurrentHashMap<>();

		public TickingServiceExample() {
			super();
		}

		@Tick(50)
		public void tick50(){
			if(!methodCallTimestamps.containsKey("tick50")){
				methodCallTimestamps.put("tick50", new ConcurrentLinkedQueue<>());
			}
			methodCallTimestamps.get("tick50").add(System.currentTimeMillis());
		}


		@Tick(100)
		public void tick100(){
			if(!methodCallTimestamps.containsKey("tick100")){
				methodCallTimestamps.put("tick100", new ConcurrentLinkedQueue<>());
			}
			methodCallTimestamps.get("tick100").add(System.currentTimeMillis());
		}
	}
}
