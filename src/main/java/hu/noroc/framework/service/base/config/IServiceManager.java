package hu.noroc.framework.service.base.config;

import hu.noroc.framework.events.IConfigurationEvent;
import hu.noroc.framework.service.IMasterService;

/**
 * One of the most basic available services.
 *
 * Should be able to initialize and start a service via Framework.
 * Responsible for:
 *  a) Finding and starting the following services:
 *      1. IAuthenticationManager
 *      2. ISessionManager
 *      3. INetworkManager
 *  b) Receive and handle configuration events for MainServices *
 *  c) Receive and create service creation events
 *
 *
 *      * No worries. Through a MainService the sub services can still be configured,
 *      Since the IServiceConfig is pretty flexible.
 * Created by Oryk on 2017. 01. 23..
 */
public interface IServiceManager extends IMasterService {
    IServiceManager getInstance();
    void onEvent(IConfigurationEvent event);
}
