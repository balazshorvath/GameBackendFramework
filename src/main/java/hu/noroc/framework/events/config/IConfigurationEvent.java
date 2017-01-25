package hu.noroc.framework.events.config;

import hu.noroc.framework.events.IFrameworkEvent;
import hu.noroc.framework.service.base.config.model.IServiceConfig;

/**
 * Created by Oryk on 2017. 01. 25..
 */
public interface IConfigurationEvent extends IFrameworkEvent {
    String getServiceId();
    IServiceConfig getConfig();
}
