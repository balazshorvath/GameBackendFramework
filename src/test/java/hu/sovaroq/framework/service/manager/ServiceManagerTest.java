package hu.sovaroq.framework.service.manager;

import static org.junit.Assert.assertTrue;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import hu.sovaroq.framework.core.ServiceManager;
import hu.sovaroq.framework.core.logger.LogProvider;
import hu.sovaroq.framework.service.base.AbstractService;
import hu.sovaroq.framework.service.base.Service;
import hu.sovaroq.framework.service.features.Run;
import hu.sovaroq.framework.service.features.Tick;

public class ServiceManagerTest {
	ServiceManager manager;
	
	@Before
	public void startup(){
		manager = new ServiceManager(2, 20, 20, LogProvider.createLogger(ServiceManager.class));
	}
	
	@Test
	public void test() throws InterruptedException{
		
		manager.manage(TestService1.class);
		manager.manage(TestService2.class);
		
		Thread.sleep(110);
		assertTrue(((TestService2)manager.getServices().get(TestService2.class)).runnable2);
		// 110 + 400 - 510
		Thread.sleep(400);
		assertTrue(((TestService2)manager.getServices().get(TestService2.class)).runnable1);
		// 510 + 500 - 1010 
		Thread.sleep(500);
		assertTrue(((TestService1)manager.getServices().get(TestService1.class)).runnable1);
		
	}
	
	@After
	public void cleanup(){
		manager.stop();
	}


	@Service(configNeedsRestart = false, configurationClass = Object.class, configurationFile = "")
	public class TestService1 extends AbstractService<Object>{
		boolean runnable1 = false;
		Queue<Long> callTimes = new ConcurrentLinkedQueue<>(); 
		
		public TestService1() {
			super();
		}

		@Tick(100)
		public void tick100(){
			callTimes.add(System.currentTimeMillis());
		}
		
		@Run
		public void runnableMethod(){
			for(int i = 0; i < 100; i++){
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
			runnable1 = true;
		}
		
	}
	@Service(configNeedsRestart = false, configurationClass = Object.class, configurationFile = "")
	public class TestService2 extends AbstractService<Object>{
		boolean runnable1 = false, runnable2 = false;

		public TestService2() {
			super();
		}

		@Run
		public void runnableMethod1(){
			for(int i = 0; i < 50; i++){
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
			runnable1 = true;
		}
		@Run
		public void runnableMethod2(){
			for(int i = 0; i < 10; i++){
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
			runnable2 = true;
		}
	}
}
