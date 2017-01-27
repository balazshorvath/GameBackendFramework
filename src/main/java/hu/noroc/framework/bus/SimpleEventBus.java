package hu.noroc.framework.bus;

import hu.noroc.framework.annotations.EventListener;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Logger;

/**
 * Created by Oryk on 2017. 01. 27..
 */
public class SimpleEventBus implements IEventBus {
    public static final String LISTENER_METHOD_NAME = "onEvent";

    private static final Logger logger = Logger.getLogger(SimpleEventBus.class.getName());

    private final List<ListenerConfig> listeners = new ArrayList<>();
    //TODO is this the best implementation to use?
    private final Queue<?> messageQueue = new ConcurrentLinkedDeque<>();
    private final ReentrantReadWriteLock listenersLock = new ReentrantReadWriteLock();
    private final ThreadPoolExecutor threadPool = new ThreadPoolExecutor()

    @Override
    public void subscribe(Class<?> type, Object instance) {
        // Is it configured?
        EventListener conf = type.getAnnotation(EventListener.class);
        if(conf == null){
            logger.warning("Could not add instance of class '" + type.getName() + "' to listeners, since it's not annotated with @EventListener.");
            return;
        }
        // Collect data
        ListenerConfig listener = new ListenerConfig(type, instance, conf.priority());
        for (Method method : type.getMethods()) {
            if(method.getName().equals(LISTENER_METHOD_NAME)){
                if(method.getParameterCount() != 1){
                    logger.warning("onEvent method of class '" + type.getName() + "' has invalid parameter set.");
                    continue;
                }
                listener.events.add(method.getParameterTypes()[0]);
            }
        }
        // Is there any actual methods?
        if(listener.events.size() < 1){
            logger.warning("Could not add instance of class '" + type.getName() + "' to listeners, since it has no valid onEvent method.");
            return;
        }
        try {
            listenersLock.writeLock().lock();
            listeners.add(listener);
            Collections.sort(listeners);
        }finally {
            listenersLock.writeLock().unlock();
        }
    }

    @Override
    public void pushEvent(Object event) {

    }

    class ListenerConfig implements Comparable<ListenerConfig>{
        final List<Class<?>> events = new ArrayList<>();
        final Class<?> listenerType;
        final Object listener;
        final ListenerPriority priority;


        ListenerConfig(Class<?> listenerType, Object listener, ListenerPriority priority) {
            this.listenerType = listenerType;
            this.listener = listener;
            this.priority = priority;
        }

        @Override
        public int compareTo(ListenerConfig o) {
            return this.priority.getValue() - o.priority.getValue();
        }
    }
}
