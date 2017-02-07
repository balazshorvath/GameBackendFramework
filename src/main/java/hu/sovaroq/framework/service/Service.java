package hu.sovaroq.framework.service;

import org.apache.logging.log4j.Logger;

import hu.sovaroq.framework.logger.LogProvider;

/**
 * Created by Oryk on 2017. 01. 27..
 */
public abstract class Service<Config> implements IService<Config>  {
    protected final IController parent;
    protected final String serviceId;
    protected Config config;
    protected Logger log;

    public Service(IController parent, String serviceId) {
        this.parent = parent;
        this.serviceId = serviceId;
        log = LogProvider.createLogger(this.getClass());
    }

    @Override
    public String getServiceId() {
        return serviceId;
    }

    @Override
    public IController getParent() {
        return parent;
    }

    @Override
    public Config getConfig() {
        return config;
    }

}
