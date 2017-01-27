package hu.noroc.framework.events;

/**
 * Created by Oryk on 2017. 01. 27..
 */
public abstract class FrameworkEvent implements IFrameworkEvent {
    /**
     * Unique id for the next event.
     * By the time it overflows, it won't matter
     */
    private static long current = 0L;

    protected final long eventId = current++;

    @Override
    public long getEventId() {
        return eventId;
    }
}
