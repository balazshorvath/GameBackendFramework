package hu.noroc.framework.service;

/**
 * Created by Oryk on 2017. 01. 27..
 */
public abstract class Service<Config> implements IService<Config>  {
    protected final IContext parent;
    protected final String serviceId;
    protected Config config;

    public Service(IContext parent, String serviceId) {
        this.parent = parent;
        this.serviceId = serviceId;
    }

    @Override
    public String getServiceId() {
        return serviceId;
    }

    @Override
    public IContext getParent() {
        return parent;
    }

    @Override
    public Config getConfig() {
        return config;
    }

}
