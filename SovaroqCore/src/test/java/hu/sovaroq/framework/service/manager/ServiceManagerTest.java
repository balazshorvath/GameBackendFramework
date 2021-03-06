package hu.sovaroq.framework.service.manager;

import hu.sovaroq.framework.service.AbstractService;
import hu.sovaroq.framework.service.Service;
import hu.sovaroq.framework.service.features.AutoSetService;
import hu.sovaroq.framework.service.features.Run;
import hu.sovaroq.framework.service.features.Tick;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ServiceManagerTest {
    ServiceManager manager;

    @Before
    public void startup() {
        manager = new ServiceManager(2, 20, 20);
    }

    @Test
    public void test() throws InterruptedException, InstantiationException, IllegalAccessException {

        manager.manage(TestService1.class);
        manager.manage(TestService2.class);

        manager.start();

        Thread.sleep(100);

        manager.stop();

        assertTrue(((TestService2) manager.getService(TestService2.class)).runnable2);
        assertTrue(((TestService2) manager.getService(TestService2.class)).runnable1);
        assertTrue(((TestService1) manager.getService(TestService1.class)).runnable1);
        assertNotNull(((TestService2) manager.getService(TestService2.class)).service1);

    }


    @Service
    public static class TestService1 extends AbstractService<Object> {
        boolean runnable1 = false;
        boolean running = false;


        public TestService1() {
            super();
        }

        @Tick(100)
        public void tick100() {
            System.out.println("Inside: tick100(), TestService1");
        }

        @Run
        public void runnableMethod() {
            System.out.println("Inside: runnableMethod(), TestService1 - start");
            while (running) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            runnable1 = true;
            System.out.println("Inside: runnableMethod(), TestService1 - stop");
        }

    }

    @Service
    public static class TestService2 extends AbstractService<Object> {
        boolean runnable1 = false, runnable2 = false;
        boolean running = false;

        @AutoSetService
        TestService1 service1;

        public TestService2() {
            super();
        }

        @Run
        public void runnableMethod1() {
            System.out.println("Inside: runnableMethod1(), TestService2 - start");
            while (running) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            runnable1 = true;
            System.out.println("Inside: runnableMethod1(), TestService2 - stop");
        }

        @Run
        public void runnableMethod2() {
            System.out.println("Inside: runnableMethod2(), TestService2 - start");
            while (running) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            runnable2 = true;
            System.out.println("Inside: runnableMethod2(), TestService2 - stop");
        }
    }
}
