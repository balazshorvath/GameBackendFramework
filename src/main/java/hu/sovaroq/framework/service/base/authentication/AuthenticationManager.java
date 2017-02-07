package hu.sovaroq.framework.service.base.authentication;

import hu.sovaroq.framework.service.AbstractService;
import hu.sovaroq.framework.service.IController;

/**
 * Possible usage:
 *      WebAuthenticationManager - used for administration site, uses MongoDB for example.
 *      GameAuthenticationManager - used for in-game auth, uses MySQL. Fires special event.
 *
 * Created by Oryk on 2017. 01. 23..
 */
public class AuthenticationManager extends AbstractService<AuthenticationManager.AuthenticationConfig> {
    public AuthenticationManager(IController parent, String serviceId) {
        super(parent, serviceId);
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
