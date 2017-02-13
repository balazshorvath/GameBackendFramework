package hu.sovaroq.framework.database;

import hu.sovaroq.framework.user.IUser;
import lombok.Value;

public interface IDatabaseServiceEvents {
	
	public enum DatabaseFailiureStatus{
		entity_not_found,
		entity_exists,
		generic_error;
	}

	@Value
	public class GetUserReqest{
		String logon;
	}
	
	@Value
	public class GetUserSuccessResponse{
		IUser user;
	}
	
	@Value
	public class GetUserFailureResponse{
		DatabaseFailiureStatus status;
	}
	
	@Value
	public class CreateUserReqest{
		String logon;
		String password;
	}
	
	@Value
	public class CreateUserSuccessResponse{
		IUser user;
	}
	
	@Value
	public class CreateUserFailureResponse{
		DatabaseFailiureStatus user;
	}
	
}
