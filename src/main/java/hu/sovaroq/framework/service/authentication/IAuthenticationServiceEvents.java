package hu.sovaroq.framework.service.authentication;

import hu.sovaroq.framework.core.eventbase.FrameworkRequestEvent;
import hu.sovaroq.framework.core.eventbase.FrameworkResponseEvent;
import hu.sovaroq.framework.data.session.Session;
import hu.sovaroq.framework.data.user.IUser;
import lombok.EqualsAndHashCode;
import lombok.Value;

public interface IAuthenticationServiceEvents {

	public enum AuthenticationStatus {
		success, wrong_password, not_found, locked, system_failure, unknown;
	}

	public enum RegistrationStatus {
		success, login_already_registered, system_failure, unknown;
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
		Long requestId;
		Session session;
	}

	@Value
	@EqualsAndHashCode(callSuper = true)
	public class AuthenticationFailureResponse extends FrameworkResponseEvent{
		Long requestId;
		AuthenticationStatus status;
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
		Long requestId;
		IUser user;
	}

	@Value
	@EqualsAndHashCode(callSuper = true)
	public class RegisterUserFailureResponse extends FrameworkResponseEvent{
		Long requestId;
		RegistrationStatus status;
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