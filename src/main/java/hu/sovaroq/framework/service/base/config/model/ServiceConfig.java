package hu.sovaroq.framework.service.base.config.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Oryk on 2017. 01. 25..
 */
@Deprecated
public class ServiceConfig implements IServiceConfig {
    Set<IConfigParameter> parameters = new HashSet<>();

    @Override
    public Set<IConfigParameter> getParameters() {
        return parameters;
    }

    @Override
    public IConfigParameter getParameter(String name) {
        return parameters.stream().filter(iConfigParameter -> name.equals(iConfigParameter.getName())).findFirst().get();
    }

    @Override
    public void setParameter(IConfigParameter parameter) {
        if(parameter == null)
            return;

        if(!parameters.contains(parameter)){
            parameters.add(parameter);
            return;
        }
        IConfigParameter iConfigParameter = parameters.stream().filter(parameter::equals).findFirst().get();
        if(parameter.getType().equals(iConfigParameter.getType()))
        iConfigParameter.setValue(parameter.getValue());
    }
}
