package hu.sovaroq.framework.core;

import java.util.ArrayList;
import java.util.List;

import hu.sovaroq.framework.controller.base.AbstractController;
import hu.sovaroq.framework.controller.base.Context;
import hu.sovaroq.framework.service.base.AbstractService;
import org.apache.logging.log4j.Logger;

import hu.sovaroq.framework.controller.base.IController;
import hu.sovaroq.framework.core.configuration.ConfigurationCreator;
import hu.sovaroq.framework.core.logger.LogProvider;

public class Framework implements IFramework {
    private static final Logger log = LogProvider.createLogger(Framework.class);

    List<AbstractController> controllers = new ArrayList<>();
    private ServiceManager manager;
    
	@Override
	public void start(List<Class<?>> features) {
		manager = new ServiceManager(20, 100, 100);

		features.forEach(this::registerController);
	}

	@Override
	public void stop() {
		controllers.forEach(AbstractController::stop);
		manager.stop();
	}

	private void registerController(Class<?> c) {
		try {
			AbstractController controller = (AbstractController) c.newInstance();
			// TODO should fine for the most part, but this is crap. Somehow need to create a proper context.
			controller.start(new Context(manager));
			controllers.add(controller);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
	}
	@Override
	public List<AbstractController> getControllers() {
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
