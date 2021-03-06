package hu.sovaroq.framework.eventing.events;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.concurrent.atomic.AtomicLong;

@EqualsAndHashCode
@ToString
public abstract class FrameworkRequestEvent implements IFrameworkEvent {
    /**
     * Shared by all requests in the system
     */
    private final static AtomicLong gen = new AtomicLong();
    @Getter
    private final Long requestId = gen.addAndGet(1);
}
