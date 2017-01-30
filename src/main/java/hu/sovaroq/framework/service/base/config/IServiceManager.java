package hu.sovaroq.framework.service.base.config;

import hu.sovaroq.framework.service.IService;

/**
 * One of the most basic available services.
 *
 * Should be able to initialize and start a service via Framework.
 * Responsible for:
 *  a) Finding and starting the following services:
 *      1. AuthenticationManager
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
public interface IServiceManager extends IService {
    IServiceManager getInstance();
}
