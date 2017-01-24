package hu.noroc.framework.service;

import hu.noroc.framework.service.base.config.model.IServiceConfig;

/**
 * Created by Oryk on 2017. 01. 23..
 */
public interface IConfigurableService<C extends IServiceConfig> {
    void configure(C config);
    C getConfig();

}
