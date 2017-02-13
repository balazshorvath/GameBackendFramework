package hu.sovaroq.framework.service.base.authentication;

import hu.sovaroq.framework.user.IUser;
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
	
	public class RegisterUserRequest{
		String logon;
		String password;
	}
	
	public class RegisterUserSuccessResponse{
		IUser user;
	}
	
	public class RegisterUserFailureResponse{
		RegistrationFaliureStatus status;
	}
	
	

}
