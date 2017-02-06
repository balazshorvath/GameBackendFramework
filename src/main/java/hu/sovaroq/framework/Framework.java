package hu.sovaroq.framework;

import java.util.List;

import org.apache.logging.log4j.Logger;

import com.google.common.util.concurrent.Service;

import hu.sovaroq.framework.configuration.ConfigurationCreator;
import hu.sovaroq.framework.logger.LogProvider;
import hu.sovaroq.framework.service.IController;

public class Framework implements IFramework {
    private static final Logger log = LogProvider.createLogger(ConfigurationCreator.class);
    private static String configFolder = "";
	

	@Override
	public void start() {
		// TODO Auto-generated method stub

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
		hu.sovaroq.framework.annotations.Service config = c.getAnnotation(hu.sovaroq.framework.annotations.Service.class);
		
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
