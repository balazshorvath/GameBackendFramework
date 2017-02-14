package hu.sovaroq.framework.service.manager;

import static org.junit.Assert.*;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.junit.Test;

import hu.sovaroq.framework.annotations.Service;
import hu.sovaroq.framework.logger.LogProvider;
import hu.sovaroq.framework.service.AbstractService;
import hu.sovaroq.framework.service.ServiceManager;
import hu.sovaroq.framework.service.extension.Run;
import hu.sovaroq.framework.service.extension.Tick;

public class ServiceManagerTest {
	
	@Test
	public void test() throws InterruptedException{
		ServiceManager manager = new ServiceManager(2, 20, 20, LogProvider.createLogger(ServiceManager.class));

		
		manager.manage(TestService1.class);
		manager.manage(TestService2.class);
		
		Thread.sleep(110);
		assertTrue(((TestService2)manager.getServices().get(TestService2.class)).runnable2);
		Thread.sleep(400);
		assertTrue(((TestService2)manager.getServices().get(TestService2.class)).runnable2);
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
