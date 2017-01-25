package hu.noroc.framework.service.base.config.model;

import java.util.Set;

/**
 * This is just an abstract definition of a config.
 * Services don't have to use this class to configure themselves.
 *
 * Created by Oryk on 2017. 01. 23..
 */
public interface IServiceConfig {
    Set<IConfigParameter> getParameters();
    IConfigParameter getParameter(String name);
    void setParameter(IConfigParameter parameter);
}
