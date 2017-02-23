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

	@Value
	@EqualsAndHashCode(callSuper = true)
	public class GetUserRequest extends FrameworkRequestEvent{
		String logon;
	}
	
	@Value
	@EqualsAndHashCode(callSuper = true)
	public class GetUserSuccessResponse extends FrameworkResponseEvent{
		IUser user;
	}
	
	@Value
	@EqualsAndHashCode(callSuper = true)
	public class GetUserFailureResponse extends FrameworkResponseEvent{
		DatabaseFailureStatus status;
	}
	
	@Value
	@EqualsAndHashCode(callSuper = true)
	public class CreateUserReqest extends FrameworkRequestEvent{
		String logon;
		String password;
	}
	
	@Value
	@EqualsAndHashCode(callSuper = true)
	public class CreateUserSuccessResponse extends FrameworkResponseEvent{
		IUser user;
	}
	
	@Value
	@EqualsAndHashCode(callSuper = true)
	public class CreateUserFailureResponse extends FrameworkResponseEvent{
		DatabaseFailureStatus user;
	}
	
}
