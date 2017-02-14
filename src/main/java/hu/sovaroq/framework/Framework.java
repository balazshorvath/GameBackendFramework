package hu.sovaroq.framework;

import java.util.List;

import org.apache.logging.log4j.Logger;

import com.google.common.util.concurrent.Service;

import hu.sovaroq.framework.controller.FrameworkController;
import hu.sovaroq.framework.controller.base.IController;
import hu.sovaroq.framework.features.configuration.ConfigurationCreator;
import hu.sovaroq.framework.features.logger.LogProvider;

public class Framework implements IFramework {
    private static final Logger log = LogProvider.createLogger(Framework.class);
    
	@Override
	public void start() {
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

	@Override
	public IFramework getInstance() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends IController> void registerController(Class<T> c, T controller) {
		hu.sovaroq.framework.service.base.Service config = c.getAnnotation(hu.sovaroq.framework.service.base.Service.class);
		
		Object conf = null;
		
		if(config != null && config.configurationClass() != null){
			conf = new ConfigurationCreator().createConfig(config.configurationClass(), findConfigurationFile(c));
		}else {
			log.info("Controller has no configuration type. Starting it with null.");
		}
		
		
	}

	private String findConfigurationFile(Class<?> c) {
		c.getName();
		return null;
	}

	@Override
	public IController getControllerById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IController> getControllers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getStatusDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double getWorkload() {
		// TODO Auto-generated method stub
		return null;
	}

}
