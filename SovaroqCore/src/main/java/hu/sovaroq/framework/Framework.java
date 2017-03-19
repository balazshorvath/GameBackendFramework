package hu.sovaroq.framework;

import hu.sovaroq.framework.controller.AbstractController;
import hu.sovaroq.framework.controller.Context;
import hu.sovaroq.framework.logger.LogProvider;
import hu.sovaroq.framework.service.manager.ServiceManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Framework implements IFramework {
    private static final Logger log = LogProvider.createLogger(Framework.class);

    private Map<Class<? extends AbstractController>, AbstractController> controllers = new HashMap<>();
    private ServiceManager manager;
    private boolean debug = false;

    @Override
    public void start(List<Class<? extends AbstractController>> features) {
        manager = new ServiceManager(5, 10, 10);
        features.forEach(this::registerController);
        manager.start();
    }

    @Override
    public void stop() {
        controllers.values().forEach(AbstractController::stop);
        controllers.clear();
        manager.stop();
    }

    private void registerController(Class<? extends AbstractController> c) {
        try {
            AbstractController controller = c.newInstance();
            // TODO should fine for the most part, but this is crap. Somehow need to create a proper context.
            controller.start(new Context(manager, debug));
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
    public void setDebug(boolean debug) {
        this.debug = debug;
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
