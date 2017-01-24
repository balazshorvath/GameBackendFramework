package hu.noroc.framework.service.base.config.model;

import java.util.List;

/**
 * Created by Oryk on 2017. 01. 23..
 */
public interface IServiceConfig {
    List<IConfigParameter> getParameters();
    IConfigParameter getParameter(String name);
    void setParameter(IConfigParameter parameter);
}
