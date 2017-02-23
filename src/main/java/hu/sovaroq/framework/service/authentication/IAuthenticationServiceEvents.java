package hu.sovaroq.framework.service.authentication;

import hu.sovaroq.framework.core.eventbase.FrameworkRequestEvent;
import hu.sovaroq.framework.core.eventbase.FrameworkResponseEvent;
import hu.sovaroq.framework.data.user.IUser;
import lombok.EqualsAndHashCode;
import lombok.Value;

public class IAuthenticationServiceEvents {

	public enum AuthenticationFailureStatus {
		wrong_password, not_found, locked, system_failure, unknowk;
	}

	public enum RegistrationFailureStatus {
		login_already_registered, system_failure, unknown;
	}

	@Value
	@EqualsAndHashCode(callSuper = true)
	public class AuthenticationRequest extends FrameworkRequestEvent{
		String logon;
		String password;
	}

	@Value
	@EqualsAndHashCode(callSuper = true)
	public class AuthenticationSuccessResponse extends FrameworkResponseEvent{
		IUser user;
	}

	@Value
	@EqualsAndHashCode(callSuper = true)
	public class AuthenticationFailureResponse extends FrameworkResponseEvent{
		AuthenticationFailureStatus status;
	}

	@Value
	@EqualsAndHashCode(callSuper = true)
	public class RegisterUserRequest extends FrameworkRequestEvent{
		String logon;
		String password;
	}

	@Value
	@EqualsAndHashCode(callSuper = true)
	public class RegisterUserSuccessResponse extends FrameworkResponseEvent{
		IUser user;
	}

	@Value
	@EqualsAndHashCode(callSuper = true)
	public class RegisterUserFailureResponse extends FrameworkResponseEvent{
		RegistrationFailureStatus status;
	}

}