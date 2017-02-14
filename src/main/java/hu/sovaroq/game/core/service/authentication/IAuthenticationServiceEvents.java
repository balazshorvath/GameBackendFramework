package hu.sovaroq.game.core.service.authentication;

import hu.sovaroq.framework.features.user.IUser;
import lombok.Value;

public class IAuthenticationServiceEvents {
	
	public enum AuthenticationFaliureStatus{
		wrong_password,
		not_found,
		locked,
		system_failure,
		unknowk;
	}
	
	public enum RegistrationFaliureStatus{
		login_already_registered,
		system_failure,
		unknown;
	}
	
	@Value
	public class AuthenticationRequest{
		String logon;
		String password;
	}
	
	@Value
	public class AuthenticationSuccessResponse{
		IUser user;
	}
	
	@Value
	public class AuthenticationFailureResponse{
		AuthenticationFaliureStatus status;
	}
	
	@Value
	public class RegisterUserRequest{
		String logon;
		String password;
	}
	
	@Value
	public class RegisterUserSuccessResponse{
		IUser user;
	}
	
	@Value
	public class RegisterUserFailureResponse{
		RegistrationFaliureStatus status;
	}
	
	

}
