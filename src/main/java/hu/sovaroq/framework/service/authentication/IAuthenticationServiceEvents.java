package hu.sovaroq.framework.service.authentication;

import hu.sovaroq.framework.core.eventbase.FrameworkRequestEvent;
import hu.sovaroq.framework.core.eventbase.FrameworkResponseEvent;
import hu.sovaroq.framework.data.user.IUser;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;

public interface IAuthenticationServiceEvents {

	public enum AuthenticationFailureStatus {
		wrong_password, not_found, locked, system_failure, unknown;
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
		long requestId;
		IUser user;
	}

	@Value
	@EqualsAndHashCode(callSuper = true)
	public class AuthenticationFailureResponse extends FrameworkResponseEvent{
		long requestId;
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
		long requestId;
		IUser user;
	}

	@Value
	@EqualsAndHashCode(callSuper = true)
	public class RegisterUserFailureResponse extends FrameworkResponseEvent{
		long requestId;
		RegistrationFailureStatus status;
	}
	
	@Value
	@EqualsAndHashCode(callSuper = true)
	public class UpdatePasswordRequest extends FrameworkRequestEvent{
		IUser user;
		String newPassword;
	}
	
	@Value
	@EqualsAndHashCode(callSuper = true)
	public class UpdatePasswordSuccessResponse extends FrameworkResponseEvent{
		IUser user;
	}
	
	@Value
	@EqualsAndHashCode(callSuper = true)
	public class UpdatePasswordFailureResponse extends FrameworkResponseEvent{
		IUser user;
	}

}