package hu.noroc.framework.service;

import hu.noroc.framework.events.config.IConfigurationEvent;
import hu.noroc.framework.service.base.config.model.IServiceConfig;

/**
 * Created by Oryk on 2017. 01. 23..
 */
public interface IConfigurableService<E extends IConfigurationEvent> {
    void onEvent(E config);
    IServiceConfig getConfig();

}
