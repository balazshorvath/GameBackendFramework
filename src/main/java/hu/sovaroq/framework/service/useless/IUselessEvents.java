package hu.sovaroq.framework.service.useless;

import hu.sovaroq.framework.core.eventbase.FrameworkRequestEvent;
import hu.sovaroq.framework.core.eventbase.FrameworkResponseEvent;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * For test purposes.
 *
 * Created by balazs_horvath on 2/23/2017.
 */
public interface IUselessEvents {

    @Value
    @EqualsAndHashCode(callSuper = true)
    public class RequestNothing extends FrameworkRequestEvent {
    }
    @Value
    @EqualsAndHashCode(callSuper = true)
    @AllArgsConstructor
    public class HereIsNothing extends FrameworkResponseEvent {
        String uselessInformation;
    }
}
