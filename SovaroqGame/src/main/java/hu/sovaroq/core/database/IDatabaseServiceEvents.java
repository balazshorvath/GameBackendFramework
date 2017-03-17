package hu.sovaroq.core.database;

import hu.sovaroq.framework.eventing.events.FrameworkRequestEvent;
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
