package hu.sovaroq.framework.service.base;

import org.apache.logging.log4j.Logger;

import hu.sovaroq.framework.core.bus.IEventBus;
import hu.sovaroq.framework.core.logger.LogProvider;
import hu.sovaroq.framework.service.database.HibernateDatabaseService;
import lombok.Setter;

/**
 * Created by Oryk on 2017. 01. 27..
 */
public class AbstractService<Config> implements IService<Config>  {
    protected Config config;
    protected Logger log = LogProvider.createLogger(getClass());
    private IEventBus bus;
    
    @Setter
    protected HibernateDatabaseService databaseService;

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
