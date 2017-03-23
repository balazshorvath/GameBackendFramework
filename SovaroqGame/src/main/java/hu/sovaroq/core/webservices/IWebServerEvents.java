package hu.sovaroq.core.webservices;

import org.eclipse.jetty.servlet.ServletContextHandler;

import hu.sovaroq.framework.eventing.events.FrameworkRequestEvent;
import lombok.EqualsAndHashCode;
import lombok.Value;

public interface IWebServerEvents {

    @Value
    @EqualsAndHashCode(callSuper = true)
    public class RegisterHandlerRequest extends FrameworkRequestEvent {
    	ServletContextHandler handler;
    }
	
}
