package hu.sovaroq.framework.service.authentication;

import org.mindrot.jbcrypt.BCrypt;

import hu.sovaroq.framework.data.user.User;
import hu.sovaroq.framework.database.UserRepository;
import hu.sovaroq.framework.exception.FrameworkException;
import hu.sovaroq.framework.service.authentication.IAuthenticationServiceEvents.AuthenticationRequest;
import hu.sovaroq.framework.service.authentication.IAuthenticationServiceEvents.RegisterUserRequest;
import hu.sovaroq.framework.service.authentication.IAuthenticationServiceEvents.RegistrationFailureStatus;
import hu.sovaroq.framework.service.base.AbstractService;

/**
 * Possible usage: WebAuthenticationManager used for administration site, uses
 * MongoDB for example. GameAuthenticationManager used for ingame auth, uses
 * MySQL. Fires special event.
 *
 * Created by Oryk on 2017. 01. 23..
 */
public class AuthenticationService extends AbstractService<AuthenticationService.AuthenticationConfig> {

	public AuthenticationService() {
		super();
	}

	public void onEvent(RegisterUserRequest request) {
		String hashedPW = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());
		UserRepository repository = (UserRepository) databaseService.getRepository(User.class);		
		User user = repository.findByLogon(request.getLogon());
		if(user != null){
			post(new IAuthenticationServiceEvents.RegisterUserFailureResponse(request.getRequestId(), RegistrationFailureStatus.login_already_registered));
		}else{
			user = new User();
			user.setLogin(request.getLogon());
			user.setPassword(hashedPW);
			try {
				repository.save(user);
				post(new IAuthenticationServiceEvents.RegisterUserSuccessResponse(request.getRequestId(), user));
			} catch (FrameworkException e) {
				post(new IAuthenticationServiceEvents.RegisterUserFailureResponse(request.getRequestId(), RegistrationFailureStatus.system_failure));
			}			
		}		
	}

	public void onEvent(AuthenticationRequest request) {
		
	}

	@Override
	public void start(AuthenticationConfig authenticationConfig) {

	}

	@Override
	public void stop() {

	}

	@Override
	public void restart() {

	}

	@Override
	public void setConfig(AuthenticationConfig authenticationConfig) {

	}

	@Override
	public String getStatusDescription() {
		return null;
	}

	@Override
	public Double getWorkload() {
		return null;
	}

	class AuthenticationConfig {

	}

}
