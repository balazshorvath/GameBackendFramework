package hu.sovaroq.framework.bus;

import hu.sovaroq.framework.eventing.bus.EventListener;
import hu.sovaroq.framework.eventing.bus.IEventBus;
import hu.sovaroq.framework.eventing.bus.SimpleEventBus;
import hu.sovaroq.framework.eventing.events.IFrameworkEvent;
import hu.sovaroq.framework.service.IService;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by Oryk on 2017. 01. 28..
 */
public class SimpleEventBusTest {
    IEventBus bus;
    TestEventReceiver1 service1;
    TestEventReceiver2 service2;

    @Before
    @SuppressWarnings("unchecked")
    public void setup() {
        bus = new SimpleEventBus(10, 30, 60, TimeUnit.SECONDS);

        service1 = mock(TestEventReceiver1.class);
        service2 = mock(TestEventReceiver2.class);
    }

    @Test
    public void testProperUsage() {
        bus.start();
        bus.subscribe(service1);
        bus.subscribe(service2);

        bus.pushEvent(new TestEvent1());

        verify(service1, timeout(100).times(1)).onEvent(any(TestEvent1.class));
        verify(service1, after(100).never()).onEvent(any(TestEvent2.class));
        verify(service2, never()).onEvent(any(TestEvent2.class));
        verify(service2, never()).onEvent(any(TestEvent3.class));


        bus.stop(50);
    }

    public void testDebugPort() {
        DebugPort port = new DebugPort();
        bus.start();

        bus.pushEvent(new TestEvent2());
        bus.registerDebugPort(port);
        bus.pushEvent(new TestEvent1());
        bus.pushEvent(new TestEvent1());
        bus.unregisterDebugPort();
        bus.pushEvent(new TestEvent1());

        bus.stop(50);

        assertEquals(2, port.integer.get());
    }

    @EventListener
    public interface TestEventReceiver1 extends IService<Object> {
        void onEvent(TestEvent1 event);

        void onEvent(TestEvent2 event);
    }

    @EventListener
    public interface TestEventReceiver2 extends IService<Object> {
        void onEvent(TestEvent3 event);

        void onEvent(TestEvent2 event);
    }

    public class TestEvent1 implements IFrameworkEvent {
    }

    public class TestEvent2 implements IFrameworkEvent {
    }

    public class TestEvent3 implements IFrameworkEvent {
    }

    public class DebugPort implements IEventBus.IEventBusDebugPort {
        AtomicInteger integer = new AtomicInteger(0);

        @Override
        public void newEvent(Object event) {
            integer.incrementAndGet();
        }
    }
}
