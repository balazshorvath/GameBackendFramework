package hu.noroc.framework.events.service;

import hu.noroc.framework.events.IFrameworkEvent;
import hu.noroc.framework.service.IService;

/**
 * Created by Oryk on 2017. 01. 23..
 */
public interface IServiceStartedEvent extends IFrameworkEvent {
    Class<? extends IService> getServiceType();
    String getServiceId();
}
