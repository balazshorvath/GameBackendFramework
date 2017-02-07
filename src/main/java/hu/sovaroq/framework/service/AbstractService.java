package hu.sovaroq.framework.service;

/**
 * Created by Oryk on 2017. 01. 27..
 */
public abstract class AbstractService<Config> implements IService<Config>  {
    protected final IController parent;
    protected final String serviceId;
    protected Config config;

    public AbstractService(IController parent, String serviceId) {
        this.parent = parent;
        this.serviceId = serviceId;
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
