package hu.sovaroq.framework.service.authentication;

import java.util.HashMap;
import java.util.Map;

import org.mindrot.jbcrypt.BCrypt;

import hu.sovaroq.framework.core.eventbase.IFrameworkEvent;
import hu.sovaroq.framework.service.authentication.IAuthenticationServiceEvents.AuthenticationRequest;
import hu.sovaroq.framework.service.authentication.IAuthenticationServiceEvents.RegisterUserRequest;
import hu.sovaroq.framework.service.base.AbstractService;
import hu.sovaroq.framework.service.database.IDatabaseServiceEvents.CreateUserFailureResponse;
import hu.sovaroq.framework.service.database.IDatabaseServiceEvents.CreateUserReqest;
import hu.sovaroq.framework.service.database.IDatabaseServiceEvents.CreateUserSuccessResponse;
import hu.sovaroq.framework.service.database.IDatabaseServiceEvents.GetUserFailureResponse;
import hu.sovaroq.framework.service.database.IDatabaseServiceEvents.GetUserRequest;
import hu.sovaroq.framework.service.database.IDatabaseServiceEvents.GetUserSuccessResponse;

/**
 * Possible usage: WebAuthenticationManager used for administration site, uses
 * MongoDB for example. GameAuthenticationManager used for ingame auth, uses
 * MySQL. Fires special event.
 *
 * Created by Oryk on 2017. 01. 23..
 */
public class AuthenticationService extends AbstractService<AuthenticationService.AuthenticationConfig> {

	private Map<Long, IFrameworkEvent> authRequests = new HashMap<>();

	public AuthenticationService() {
		super();
	}

	public void onEvent(RegisterUserRequest request) {
		authRequests.put(request.getRequestId(), request);
		String hashedPW = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());
		post(new CreateUserReqest(request.getLogon(), hashedPW));
	}

	public void onEvent(CreateUserFailureResponse response) {

	}

	public void onEvent(CreateUserSuccessResponse response) {

	}

	public void onEvent(AuthenticationRequest request) {
		post(new GetUserRequest(request.getLogon()));
	}

	public void onEvent(GetUserSuccessResponse response) {

	}

	public void onEvent(GetUserFailureResponse response) {

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
