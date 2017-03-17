package hu.sovaroq.core.user.session;

import hu.sovaroq.framework.eventing.events.FrameworkRequestEvent;
import hu.sovaroq.framework.eventing.events.FrameworkResponseEvent;
import hu.sovaroq.core.user.authentication.IUser;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.UUID;

public interface ISessionServiceEvents {
	
	@Value
	@EqualsAndHashCode(callSuper = true)
	public class CreateOrGetSession extends FrameworkRequestEvent{
		UUID sessionID;
		IUser user;
	}

	@Value
	@EqualsAndHashCode(callSuper = true)
	public class CreateOrGetSessionResponse extends FrameworkResponseEvent{
		Long requestId;
		Session session;
	}
	
}
