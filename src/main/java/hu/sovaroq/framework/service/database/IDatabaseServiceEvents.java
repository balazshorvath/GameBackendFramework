package hu.sovaroq.framework.service.database;

import hu.sovaroq.framework.core.eventbase.FrameworkRequestEvent;
import hu.sovaroq.framework.core.eventbase.FrameworkResponseEvent;
import hu.sovaroq.framework.data.user.IUser;
import lombok.EqualsAndHashCode;
import lombok.Value;

public interface IDatabaseServiceEvents {
	
	public enum DatabaseFailiureStatus{
		entity_not_found,
		entity_aready_exists,
		generic_error;
	}

	@Value
	@EqualsAndHashCode(callSuper = true)
	public class GetUserReqest extends FrameworkRequestEvent{
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
		DatabaseFailiureStatus status;
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
		DatabaseFailiureStatus user;
	}
	
}
