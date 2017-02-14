package hu.sovaroq.game.core.service.authentication;

import hu.sovaroq.framework.service.base.AbstractService;
import hu.sovaroq.game.core.service.authentication.IAuthenticationServiceEvents.AuthenticationRequest;
import hu.sovaroq.game.core.service.authentication.IAuthenticationServiceEvents.RegisterUserRequest;

import org.mindrot.jbcrypt.BCrypt;

import hu.sovaroq.framework.database.IDatabaseServiceEvents;


/**
 * Possible usage:
 *      WebAuthenticationManager - used for administration site, uses MongoDB for example.
 *      GameAuthenticationManager - used for in-game auth, uses MySQL. Fires special event.
 *
 * Created by Oryk on 2017. 01. 23..
 */
public class AuthenticationService extends AbstractService<AuthenticationService.AuthenticationConfig> {
    public AuthenticationService() {
        super();
    }
    
    public void onEvent(RegisterUserRequest request){
    	String hashedPW = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());
    	
    	post(new IDatabaseServiceEvents.CreateUserReqest(request.getLogon(), hashedPW));
    }
    
    public void onEvent(AuthenticationRequest request){
    	    	
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

    class AuthenticationConfig{

    }

}
