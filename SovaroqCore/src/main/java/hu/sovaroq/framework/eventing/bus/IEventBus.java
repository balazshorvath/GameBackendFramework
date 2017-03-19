package hu.sovaroq.framework.eventing.bus;

/**
 * Created by Oryk on 2017. 01. 27..
 */
public interface IEventBus {
    void start();
    void stop(long forceTimeout);

    void subscribe(Object instance);
    void pushEvent(Object event);
    void registerDebugPort(IEventBusDebugPort port);
    void unregisterDebugPort();

    interface IEventBusDebugPort{
        void newEvent(Object event);
    }

}
