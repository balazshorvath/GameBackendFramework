package hu.sovaroq.framework.bus;

import hu.sovaroq.framework.events.FrameworkEvent;
import hu.sovaroq.framework.features.bus.EventListener;
import hu.sovaroq.framework.features.bus.IEventBus;
import hu.sovaroq.framework.features.bus.SimpleEventBus;
import hu.sovaroq.framework.service.base.IService;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

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
    public void setup(){
        bus = new SimpleEventBus(10, 30, 60, TimeUnit.SECONDS);

        service1 = mock(TestEventReceiver1.class);
        service2 = mock(TestEventReceiver2.class);
    }

    @Test
    public void testProperUsage() {
        bus.start();
        bus.subscribe(TestEventReceiver1.class, service1);
        bus.subscribe(TestEventReceiver2.class, service2);

        bus.pushEvent(new TestEvent1());

        verify(service1, timeout(100).times(1)).onEvent(any(TestEvent1.class));
        verify(service1, after(100).never()).onEvent(any(TestEvent2.class));
        verify(service2, never()).onEvent(any(TestEvent2.class));
        verify(service2, never()).onEvent(any(TestEvent3.class));

        bus.pushEvent(new TestEvent2());
        bus.pushEvent(new TestEvent3());
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

    public class TestEvent1 extends FrameworkEvent {
    }
    public class TestEvent2 extends FrameworkEvent {
    }
    public class TestEvent3 extends FrameworkEvent {
    }
}
