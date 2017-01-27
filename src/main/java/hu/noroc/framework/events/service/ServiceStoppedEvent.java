package hu.noroc.framework.events.service;

import hu.noroc.framework.events.FrameworkEvent;
import hu.noroc.framework.service.IService;

/**
 * Created by Oryk on 2017. 01. 24..
 */
public class ServiceStoppedEvent extends FrameworkEvent {

    Class<? extends IService> getServiceType() {
        return null;
    }

    String getServiceId() {
        return null;
    }
}
