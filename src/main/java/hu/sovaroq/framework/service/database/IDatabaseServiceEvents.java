package hu.sovaroq.framework.service.database;

import hu.sovaroq.framework.core.eventbase.FrameworkRequestEvent;
import hu.sovaroq.framework.core.eventbase.FrameworkResponseEvent;
import hu.sovaroq.framework.data.user.IUser;
import lombok.EqualsAndHashCode;
import lombok.Value;

public interface IDatabaseServiceEvents {
	
	public enum DatabaseFailureStatus {
		entity_not_found,
		entity_aready_exists,
		generic_error;
	}

	@Value
	@EqualsAndHashCode(callSuper = true)
	public class DatabaseServiceRestarted extends FrameworkRequestEvent{
	}

	@Value
	@EqualsAndHashCode(callSuper = true)
	public class DatabaseServiceStopped extends FrameworkRequestEvent{
	}

	
}
