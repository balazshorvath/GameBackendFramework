package hu.sovaroq.framework.core;

import hu.sovaroq.framework.controller.base.AbstractController;
import hu.sovaroq.framework.controller.base.Context;
import hu.sovaroq.framework.core.command.FrameworkCommand;
import hu.sovaroq.framework.core.command.ServiceCommand;
import hu.sovaroq.framework.core.logger.LogProvider;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Framework implements IFramework {
    private static final Logger log = LogProvider.createLogger(Framework.class);

    Map<Class<? extends AbstractController>, AbstractController> controllers = new HashMap<>();
    private ServiceManager manager;

    @Override
	public void start(List<Class<? extends AbstractController>> features) {
		manager = new ServiceManager(20, 100, 100);

		features.forEach(this::registerController);
	}

	@Override
	public void stop() {
		controllers.values().forEach(AbstractController::stop);
		controllers.clear();
		manager.stop();
	}

    @Override
    public Object execute(FrameworkCommand command) {
        if(command instanceof ServiceCommand){

        }
        return null;
    }

    private void registerController(Class<? extends AbstractController> c) {
        try {
            AbstractController controller = c.newInstance();
            // TODO should fine for the most part, but this is crap. Somehow need to create a proper context.
            controller.start(new Context(manager));
            controllers.put(c, controller);
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
    }
    private void unregisterController(Class<? extends AbstractController> c) {
	    AbstractController controller = controllers.get(c);
        controller.stop();
        controllers.remove(c);
    }

	@Override
	public List<AbstractController> getControllers() {
		return (List<AbstractController>) controllers.values();
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

    public ServiceManager getManager() {
        return manager;
    }
}
