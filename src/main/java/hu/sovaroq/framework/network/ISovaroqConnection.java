package hu.sovaroq.framework.network;

import hu.sovaroq.framework.core.eventbase.IFrameworkEvent;
import hu.sovaroq.framework.exception.FrameworkException;

import java.util.concurrent.TimeUnit;

/**
 * Created by Oryk on 2017. 03. 07..
 */
public interface ISovaroqConnection {
    IFrameworkEvent poll(long timeout, TimeUnit unit);
    IFrameworkEvent peek();

    /**
     * Sends the event immediately.
     *
     * @param o
     * @throws FrameworkException
     */
    void send(IFrameworkEvent o) throws FrameworkException;

    /**
     * Saves events into a queue. When flush() is called,
     * the events are put into a wrapper and sent to the client.
     *
     * @param o
     */
    void cache(IFrameworkEvent o);

    /**
     * Wrap the events from the queue anf write the to the socket.
     *
     * The flush might send multiple wrapper messages depending on
     * how much events there are in the queue.
     *
     * @throws FrameworkException if the connection is not alive.
     */
    void flush() throws FrameworkException;

    boolean isOpen();

}
