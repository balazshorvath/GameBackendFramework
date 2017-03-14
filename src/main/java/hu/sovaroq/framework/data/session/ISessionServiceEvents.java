package hu.sovaroq.framework.data.session;

import java.util.UUID;

import hu.sovaroq.framework.core.eventbase.FrameworkRequestEvent;
import hu.sovaroq.framework.core.eventbase.FrameworkResponseEvent;
import hu.sovaroq.framework.data.user.IUser;
import lombok.EqualsAndHashCode;
import lombok.Value;

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
