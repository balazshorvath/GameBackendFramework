package hu.sovaroq.framework.service;

import org.apache.logging.log4j.Logger;

import hu.sovaroq.framework.bus.IEventBus;
import hu.sovaroq.framework.logger.LogProvider;

/**
 * Created by Oryk on 2017. 01. 27..
 */
public abstract class AbstractService<Config> implements IService<Config>  {
    protected final String serviceId;
    protected Config config;
    protected Logger log;
    private IEventBus bus;

    public AbstractService(String serviceId, IEventBus bus) {
        this.serviceId = serviceId;
        log = LogProvider.createLogger(this.getClass());
        this.bus = bus;
    }
    
    protected void post(Object o){
    	this.bus.pushEvent(o);
    }

    @Override
    public void start(Config config){
    	this.config = config;
    }
    
	@Override
    public void stop(){
    }
	
	@Override
    public void restart(){
    }
	
	@Override
	public void setConfig(Config config) {
    	this.config = config;
	}

	@Override
	public String getStatusDescription() {
		return null;
	}
	
	@Override
	public Double getWorkload() {
		return null;
	}
	
    @Override
    public String getServiceId() {
        return serviceId;
    }

    @Override
    public Config getConfig() {
        return config;
    }

}
