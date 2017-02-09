package hu.sovaroq.framework.service.ticker;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import hu.sovaroq.framework.service.AbstractService;
import hu.sovaroq.framework.service.IController;
import hu.sovaroq.framework.service.extension.Tick;
import hu.sovaroq.framework.service.extension.Ticker;

public class TickerTest {
	private Ticker ticker;
	private Method m1;
	private Method m2;
	private Method m3;
	
	private List<TickingServiceExample> testServices = new ArrayList<>();
	
	@Before
	public void setup() throws NoSuchMethodException, SecurityException{
		ticker = new Ticker(50);
		
		m1 = TickingServiceExample.class.getMethod("tick30");
		m2 = TickingServiceExample.class.getMethod("tick50");
		m3 = TickingServiceExample.class.getMethod("tick100");
	}
	@Test
	public void testTiming() throws InterruptedException{
		for (int i = 0; i < 50; i++) {
			TickingServiceExample example = new TickingServiceExample(Mockito.mock(IController.class), "RandomString");
			
			ticker.addTickerCall(m1.getAnnotation(Tick.class).value(), m1, example);
			ticker.addTickerCall(m2.getAnnotation(Tick.class).value(), m2, example);
			ticker.addTickerCall(m3.getAnnotation(Tick.class).value(), m3, example);
			
			testServices.add(example);
		}

		Thread t = new Thread(ticker.start());
		
		Thread.sleep(5000);
		
		ticker.stop();
		if(t.isAlive()){
			t.interrupt();
		}
		double call30Avg = 0L;
		int call30Times;
		
		double call50Avg = 0L;
		int call50Times;
		
		double call100Avg = 0L;
		int call100Times;
		
		for(TickingServiceExample e : testServices){
			Queue<Long> calls = e.methodCallTimestamps.get("tick30");
			// -1, because the first call doesn't count
			call30Times = calls.size() - 1;
			// Collect data
			if(call30Times > 1){
				long previous = calls.poll();
				while(calls.peek() != null){
					Assert.assertTrue(previous < calls.peek());
					call30Avg += previous - calls.peek();
					previous = calls.poll();
				}
				call30Avg /= (double)call30Times;
				System.out.println("30 ms method called " + call30Times + " times with an avg of " + call30Avg + " ms time difference.");
				Assert.assertTrue(call30Avg < 31 && call30Avg > 29);
			}else {
				Assert.fail();
			}
		}
	}
	
	class TickingServiceExample extends AbstractService<Object>{
		public final Map<String, Queue<Long>> methodCallTimestamps = new ConcurrentHashMap<>();

		public TickingServiceExample(IController parent, String serviceId) {
			super(parent, serviceId);
		}

		@Tick(30)
		public void tick30(){
			if(!methodCallTimestamps.containsKey("tick30")){
				methodCallTimestamps.put("tick30", new ConcurrentLinkedQueue<>());
			}
			methodCallTimestamps.get("tick30").add(System.currentTimeMillis());
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
