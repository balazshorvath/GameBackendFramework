package hu.sovaroq.framework.service.base.authentication;

import hu.sovaroq.framework.events.authentication.AuthenticateUserEvent;
import hu.sovaroq.framework.events.authentication.RegisterUserEvent;
import hu.sovaroq.framework.service.IContext;
import hu.sovaroq.framework.service.Service;

/**
 * Possible usage:
 *      WebAuthenticationManager - used for administration site, uses MongoDB for example.
 *      GameAuthenticationManager - used for in-game auth, uses MySQL. Fires special event.
 *
 * Created by Oryk on 2017. 01. 23..
 */
public class AuthenticationManager extends Service<AuthenticationManager.AuthenticationConfig> {
    public AuthenticationManager(IContext parent, String serviceId) {
        super(parent, serviceId);
    }

    //TODO
    <Event extends AuthenticateUserEvent> void onEvent(Event event) {

    }

    //TODO
    <Event extends RegisterUserEvent> void onEvent(Event event) {

    }

    @Override
    public void onCreate(AuthenticationConfig authenticationConfig) {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void restart() {

    }

    @Override
    public void setConfig(AuthenticationConfig authenticationConfig) {

    }

    class AuthenticationConfig{

    }
}
