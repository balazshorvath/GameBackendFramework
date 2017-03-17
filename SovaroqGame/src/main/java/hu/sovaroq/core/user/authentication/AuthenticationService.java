package hu.sovaroq.core.user.authentication;

import hu.sovaroq.framework.eventing.bus.EventListener;
import hu.sovaroq.framework.exception.FrameworkException;
import hu.sovaroq.framework.service.AbstractService;
import hu.sovaroq.framework.service.Service;
import hu.sovaroq.framework.service.features.AutoSetService;
import hu.sovaroq.core.user.authentication.IAuthenticationServiceEvents.AuthenticationRequest;
import hu.sovaroq.core.user.authentication.IAuthenticationServiceEvents.RegistrationStatus;
import hu.sovaroq.core.user.session.ISessionServiceEvents;
import hu.sovaroq.core.database.HibernateDatabaseService;
import hu.sovaroq.core.database.UserRepository;
import org.mindrot.jbcrypt.BCrypt;

import java.util.HashMap;
import java.util.Map;

/**
 * Possible usage: WebAuthenticationManager used for administration site, uses
 * MongoDB for example. GameAuthenticationManager used for ingame auth, uses
 * MySQL. Fires special event.
 *
 * Created by Oryk on 2017. 01. 23..
 */
@Service
@EventListener
public class AuthenticationService extends AbstractService<AuthenticationService.AuthenticationConfig> {

	Map<Long, AuthenticationRequest> sessionCreation = new HashMap<>();
	
    @AutoSetService
    protected HibernateDatabaseService databaseService;
	
	public AuthenticationService() {
		super();
	}

	public void onEvent(IAuthenticationServiceEvents.RegisterUserRequest request) {
		log.debug("received: " + request);
		String hashedPW = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());
		UserRepository repository = (UserRepository) databaseService.getRepository(User.class);
		User user = repository.findByLogon(request.getLogon());
		if(user != null){
			post(new IAuthenticationServiceEvents.RegisterUserFailureResponse(request.getRequestId(), RegistrationStatus.login_already_registered));
		}else{
			user = new User();
			user.setLogin(request.getLogon());
			user.setPassword(hashedPW);
			try {
				repository.save(user);
				post(new IAuthenticationServiceEvents.RegisterUserSuccessResponse(request.getRequestId(), user));
			} catch (FrameworkException e) {
				post(new IAuthenticationServiceEvents.RegisterUserFailureResponse(request.getRequestId(), RegistrationStatus.system_failure));
			}			
		}		
	}

	public void onEvent(IAuthenticationServiceEvents.AuthenticationRequest request) {
		log.debug("received: " + request);
		UserRepository repository = (UserRepository) databaseService.getRepository(User.class);	
		User user = repository.findByLogon(request.getLogon());
		if(user == null){
			post(new IAuthenticationServiceEvents.AuthenticationFailureResponse(request.getRequestId(), IAuthenticationServiceEvents.AuthenticationStatus.not_found));
		}else if(BCrypt.checkpw(request.getPassword(), user.getPassword())){
			if(user.isLocked()){
				post(new IAuthenticationServiceEvents.AuthenticationFailureResponse(request.getRequestId(), IAuthenticationServiceEvents.AuthenticationStatus.locked));
			}else{
				ISessionServiceEvents.CreateOrGetSession createOrGetSession = new ISessionServiceEvents.CreateOrGetSession(null, user);
				sessionCreation.put(createOrGetSession.getRequestId(), request);
				post(createOrGetSession);
			}
		}else{
			post(new IAuthenticationServiceEvents.AuthenticationFailureResponse(request.getRequestId(), IAuthenticationServiceEvents.AuthenticationStatus.wrong_password));
		}
	}
	
	public void onEvent(ISessionServiceEvents.CreateOrGetSessionResponse response){
		if(sessionCreation.containsKey(response.getRequestId())){
			post(new IAuthenticationServiceEvents.AuthenticationSuccessResponse(sessionCreation.get(response.getRequestId()).getRequestId(), response.getSession()));
		}
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
