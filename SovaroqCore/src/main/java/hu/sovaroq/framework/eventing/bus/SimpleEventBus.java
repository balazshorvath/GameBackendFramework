package hu.sovaroq.framework.eventing.bus;

import hu.sovaroq.framework.logger.LogProvider;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Oryk on 2017. 01. 27..
 */
public class SimpleEventBus implements IEventBus, Runnable {
    public static final String LISTENER_METHOD_NAME = "onEvent";

    private static final Logger logger = LogProvider.createLogger(SimpleEventBus.class);

    private final List<ListenerConfig> listeners = new ArrayList<>();
    private final BlockingQueue<Object> messageQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<Runnable> tasks = new LinkedBlockingQueue<>();
    private final ReentrantReadWriteLock listenersLock = new ReentrantReadWriteLock();
    private final ThreadPoolExecutor threadPool;

    private IEventBusDebugPort debugPort;

    private Thread consumer;
    private ConsumerState consumerState = ConsumerState.UNINITIALIZED;
    private boolean running = false;

    public SimpleEventBus(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit) {
        threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, tasks);
    }

    @Override
    public void start() {
        running = true;
        consumer = new Thread(this);
        consumer.start();
    }

    @Override
    public void stop(long forceTimeout) {
        logger.info("Shutting down the event bus.");
        running = false;
        threadPool.shutdown();

        final long startTime = System.currentTimeMillis();
        while (consumerState != ConsumerState.STOPPED && (System.currentTimeMillis() - startTime) < forceTimeout) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
                break;
            }
        }
        if (consumerState != ConsumerState.STOPPED) {
            logger.info("The consumer did not stop, calling stop().");
            consumer.interrupt();
        }
        try {
            if (!threadPool.awaitTermination(forceTimeout, TimeUnit.MILLISECONDS)) {
                logger.info("ThreadPool did not terminate, calling shutdownNow().");
                threadPool.shutdownNow();
            }
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        }
        messageQueue.clear();
        logger.info("Event bus successfully stopped.");
    }

    @Override
    public void subscribe(Object instance) {
        // Is it configured?
        Class<?> type = instance.getClass();
        EventListener conf = type.getAnnotation(EventListener.class);
        if (conf == null) {
            logger.warn("Could not add instance of class '" + type.getName() + "' to listeners, since it's not annotated with @EventListener.");
            return;
        }

        try {
            listenersLock.writeLock().lock();
            Optional<ListenerConfig> listener = listeners.stream().filter(listenerConfig -> listenerConfig.listenerType.equals(type)).findFirst();
            ListenerConfig config;

            if (!listener.isPresent()) {
                config = new ListenerConfig(type, conf.priority());
                for (Method method : type.getMethods()) {
                    if (method.getName().equals(LISTENER_METHOD_NAME)) {
                        if (method.getParameterCount() != 1) {
                            logger.warn("onEvent method of class '" + type.getName() + "' has invalid parameter set.");
                            continue;
                        }
                        logger.debug("New listener method added: " + method.getName() + " with event " + method.getParameterTypes()[0].getName());
                        config.events.put(method.getParameterTypes()[0], method);
                    }
                }
                // Is there any actual methods?
                if (config.events.size() < 1) {
                    logger.warn("Could not add instance of class '" + type.getName() + "' to listeners, since it has no valid onEvent method.");
                    return;
                }
                listeners.add(config);
            } else {
                config = listener.get();
            }
            config.instances.add(instance);

            Collections.sort(listeners);
        } finally {
            listenersLock.writeLock().unlock();
        }
    }

    @Override
    public void run() {
        Object event;
        consumerState = ConsumerState.RUNNING;
        while (running) {
            try {
                event = messageQueue.poll(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                running = false;
                continue;
            }
            if (event == null)
                continue;

            try {
                listenersLock.readLock().lock();
                if (debugPort != null) {
                    debugPort.newEvent(event);
                }
                boolean found = false;
                for (ListenerConfig listener : listeners) {
                    Method m = listener.events.get(event.getClass());
                    if (m != null) {
                        final Object e = event;
                        listener.instances.forEach(o ->
                                threadPool.execute(createInvokingThread(m, o, e))
                        );
                        found = true;
                    }
                }
                if (!found)
                    logger.debug("No listener for this event: " + event);
            } finally {
                listenersLock.readLock().unlock();
            }

        }
        consumerState = ConsumerState.STOPPED;
    }

    @Override
    public void pushEvent(Object event) {
        messageQueue.add(event);
    }

    @Override
    public void registerDebugPort(IEventBusDebugPort port) {
        try {
            listenersLock.writeLock().lock();
            this.debugPort = port;
        } finally {
            listenersLock.writeLock().unlock();
        }
    }

    @Override
    public void unregisterDebugPort() {
        this.debugPort = null;
    }

    private Runnable createInvokingThread(final Method m, final Object instance, final Object event) {
        return () -> {
            try {
                m.invoke(instance, event);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
            }
        };
    }

    enum ConsumerState {
        UNINITIALIZED,
        RUNNING,
        STOPPED
    }

    class ListenerConfig implements Comparable<ListenerConfig> {
        final Map<Class<?>, Method> events = new HashMap<>();
        final Class<?> listenerType;
        final List<Object> instances = new ArrayList<>();
        final ListenerPriority priority;


        ListenerConfig(Class<?> listenerType, ListenerPriority priority) {
            this.listenerType = listenerType;
            this.priority = priority;
        }

        @Override
        public int compareTo(ListenerConfig o) {
            return this.priority.getValue() - o.priority.getValue();
        }
    }
}
