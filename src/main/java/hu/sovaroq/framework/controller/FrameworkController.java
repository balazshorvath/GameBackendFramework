package hu.sovaroq.framework.controller;

import hu.sovaroq.framework.controller.base.AbstractController;
import hu.sovaroq.framework.controller.base.Context;
import hu.sovaroq.framework.core.command.FrameworkCommand;
import hu.sovaroq.framework.core.command.ServiceCommand;
import hu.sovaroq.framework.service.authentication.AuthenticationService;
import hu.sovaroq.framework.service.base.AbstractService;
import hu.sovaroq.framework.service.database.HibernateDatabaseService;
import hu.sovaroq.framework.service.useless.CompletelyUselessService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oryk on 2017. 01. 27..
 */
public class FrameworkController extends AbstractController<Context> {
	List<AbstractService> services = new ArrayList<>();

    @Override
    public void start(Context context) {
        log.info(">FrameworkController - start()");
    	manager = context.getManager();

		register(HibernateDatabaseService.class);
		register(CompletelyUselessService.class);
        log.info("<FrameworkController - start()");
    }

    @Override
    public void stop() {
        log.info(">FrameworkController - stop()");
        services.forEach(abstractService -> manager.stopService(abstractService.getClass()));
        services.clear();
        log.info("<FrameworkController - stop()");
    }

	@Override
	public String getStatusDescription() {
		return "FrameworkController";
	}

	@Override
	public Double getWorkload() {
		return 0.0;
	}

	@Override
	public List<AbstractService> getServices() {
		return services;
	}

	private boolean register(Class<? extends AbstractService> type){
        log.info("FrameworkController - register(" + type.getName() + ")");
		AbstractService service = manager.manage(type);
		if(service == null) {
			log.error("Could not create service " + type.getName() + "!");
			return false;
		}
		services.add(service);
		return true;
	}
}
