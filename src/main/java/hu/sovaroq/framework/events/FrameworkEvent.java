package hu.sovaroq.framework.events;

/**
 * Created by Oryk on 2017. 01. 27..
 */
public abstract class FrameworkEvent {
    /**
     * Unique id for the next event.
     * By the time it overflows, it won't matter
     */
    private static long current = 0L;

    protected final long eventId = current++;

    public long getEventId() {
        return eventId;
    }
}
