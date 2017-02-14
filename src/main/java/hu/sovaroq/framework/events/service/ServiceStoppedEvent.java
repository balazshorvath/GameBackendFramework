package hu.sovaroq.framework.events.service;

import hu.sovaroq.framework.events.FrameworkEvent;
import hu.sovaroq.framework.service.base.IService;

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
