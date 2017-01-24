package hu.noroc.framework.events;

import hu.noroc.framework.service.IService;

/**
 * Created by Oryk on 2017. 01. 23..
 */
public interface INewServiceEvent extends IFrameworkEvent {
    Class<? extends IService> getServiceType();
    String getServiceName();
}
