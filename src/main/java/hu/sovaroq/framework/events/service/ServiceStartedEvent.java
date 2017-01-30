package hu.sovaroq.framework.events.service;

import hu.sovaroq.framework.events.FrameworkEvent;
import hu.sovaroq.framework.service.IService;

/**
 * Created by Oryk on 2017. 01. 23..
 */
public class ServiceStartedEvent extends FrameworkEvent {
    Class<? extends IService> getServiceType() {
        return null;
    }

    String getServiceId() {
        return null;
    }
}
