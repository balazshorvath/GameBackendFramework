package hu.noroc.framework.bus;

import hu.noroc.framework.annotations.EventListener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Logger;

/**
 * Created by Oryk on 2017. 01. 27..
 */
public class SimpleEventBus implements IEventBus, Runnable {
    public static final String LISTENER_METHOD_NAME = "onEvent";

    private static final Logger logger = Logger.getLogger(SimpleEventBus.class.getName());

    private final List<ListenerConfig> listeners = new ArrayList<>();
    private final BlockingQueue<Object> messageQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<Runnable> tasks = new LinkedBlockingQueue<>();
    private final ReentrantReadWriteLock listenersLock = new ReentrantReadWriteLock();
    private final ThreadPoolExecutor threadPool;

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
        while (consumerState != ConsumerState.STOPPED && (System.currentTimeMillis() - startTime) < forceTimeout){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                logger.severe(e.getMessage());
                break;
            }
        }
        if(consumerState != ConsumerState.STOPPED){
            logger.info("The consumer did not stop, calling stop().");
            consumer.stop();
        }
        try {
            if(!threadPool.awaitTermination(forceTimeout, TimeUnit.MILLISECONDS)) {
                logger.info("ThreadPool did not terminate, calling shutdownNow().");
                threadPool.shutdownNow();
            }
        } catch (InterruptedException e) {
            threadPool.shutdownNow();
            logger.severe(e.getMessage());
        }
        messageQueue.clear();
        logger.info("Event bus successfully stopped.");
    }

    @Override
    public void subscribe(Class<?> type, Object instance) {
        // Is it configured?
        EventListener conf = type.getAnnotation(EventListener.class);
        if(conf == null){
            logger.warning("Could not add instance of class '" + type.getName() + "' to listeners, since it's not annotated with @EventListener.");
            return;
        }

        try {
            listenersLock.writeLock().lock();
            Optional<ListenerConfig> listener = listeners.stream().filter(listenerConfig -> listenerConfig.listenerType.equals(type)).findFirst();
            ListenerConfig config;

            if(!listener.isPresent()){
                config = new ListenerConfig(type, conf.priority());
                for (Method method : type.getMethods()) {
                    if (method.getName().equals(LISTENER_METHOD_NAME)) {
                        if (method.getParameterCount() != 1) {
                            logger.warning("onEvent method of class '" + type.getName() + "' has invalid parameter set.");
                            continue;
                        }
                        config.events.put(method.getParameterTypes()[0], method);
                    }
                }
                // Is there any actual methods?
                if (config.events.size() < 1) {
                    logger.warning("Could not add instance of class '" + type.getName() + "' to listeners, since it has no valid onEvent method.");
                    return;
                }
                listeners.add(config);
            }else {
                config = listener.get();
            }
            config.instances.add(instance);

            Collections.sort(listeners);
        }finally {
            listenersLock.writeLock().unlock();
        }
    }

    @Override
    public void pushEvent(Object event) {
        if(running)
            messageQueue.add(event);
    }

    @Override
    public void run() {
        Object event;
        consumerState = ConsumerState.RUNNING;
        while (running){
            try {
                event = messageQueue.poll(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                continue;
            }
            if(event == null)
                continue;

            try {
                listenersLock.readLock().lock();
                for (ListenerConfig listener : listeners) {
                    Method m = listener.events.get(event.getClass());
                    if(m != null){
                        final Object e = event;
                        listener.instances.forEach(o ->
                                threadPool.execute(createInvokingThread(m, o, e))
                        );
                    }
                }
            } finally {
                listenersLock.readLock().unlock();
            }

        }
        consumerState = ConsumerState.STOPPED;
    }

    private Runnable createInvokingThread(final Method m, final Object instance, final Object event){
        return () -> {
            try {
                m.invoke(instance, event);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                logger.severe(e.getMessage());
            }
        };
    }

    class ListenerConfig implements Comparable<ListenerConfig>{
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
    enum ConsumerState{
        UNINITIALIZED,
        RUNNING,
        STOPPED
    }
}
