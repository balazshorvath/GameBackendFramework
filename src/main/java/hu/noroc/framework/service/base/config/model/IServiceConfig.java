package hu.noroc.framework.service.base.config.model;

import java.util.Set;

/**
 * This is just an abstract definition of a configuration.
 *
 * Created by Oryk on 2017. 01. 23..
 */
@Deprecated
public interface IServiceConfig {
    Set<IConfigParameter> getParameters();
    IConfigParameter getParameter(String name);
    void setParameter(IConfigParameter parameter);
}
