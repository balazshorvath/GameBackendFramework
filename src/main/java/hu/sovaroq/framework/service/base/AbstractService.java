package hu.sovaroq.framework.service.base;

import org.apache.logging.log4j.Logger;

import hu.sovaroq.framework.features.bus.IEventBus;
import hu.sovaroq.framework.features.logger.LogProvider;

/**
 * Created by Oryk on 2017. 01. 27..
 */
public abstract class AbstractService<Config> implements IService<Config>  {
    protected Config config;
    protected Logger log;
    private IEventBus bus;

    public AbstractService() {
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
    public Config getConfig() {
        return config;
    }

	@Override
	public void setBus(IEventBus bus) {
		this.bus = bus;
		
	}

	@Override
	public void setLog(Logger log) {
		this.log = log;
	}

}
