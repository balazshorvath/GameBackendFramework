package hu.noroc.framework.service.base.authentication;

import hu.noroc.framework.events.authentication.IAuthenticateUserEvent;
import hu.noroc.framework.events.authentication.IRegisterUserEvent;
import hu.noroc.framework.service.IMasterService;

/**
 * Possible usage:
 *      WebAuthenticationManager - used for administration site, uses MongoDB for example.
 *      GameAuthenticationManager - used for in-game auth, uses MySQL. Fires special event.
 *
 * Created by Oryk on 2017. 01. 23..
 */
public interface IAuthenticationManager extends IMasterService {
    <Event extends IAuthenticateUserEvent> void onEvent(Event event);
    <Event extends IRegisterUserEvent> void onEvent(Event event);


}
