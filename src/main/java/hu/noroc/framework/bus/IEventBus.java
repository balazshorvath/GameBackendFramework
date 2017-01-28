package hu.noroc.framework.bus;

/**
 * Created by Oryk on 2017. 01. 27..
 */
public interface IEventBus {
    void start();
    void stop();

    void subscribe(Class<?> type, Object instance);

    void pushEvent(Object event);

}
