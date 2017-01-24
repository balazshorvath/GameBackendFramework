package hu.noroc.framework.events;

import hu.noroc.framework.service.base.config.model.IServiceConfig;

/**
 * Event sent from the administration to configure services.
 *
 * Created by Oryk on 2017. 01. 23..
 */
public interface IConfigurationEvent extends IFrameworkEvent {
    IServiceConfig getServiceConfig();
}
