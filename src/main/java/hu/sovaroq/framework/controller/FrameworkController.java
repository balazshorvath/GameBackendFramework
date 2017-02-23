package hu.sovaroq.framework.controller;

import hu.sovaroq.framework.controller.base.AbstractController;
import hu.sovaroq.framework.controller.base.Context;
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
    	manager = context.getManager();

		register(HibernateDatabaseService.class);
		register(CompletelyUselessService.class);
    }

    @Override
    public void stop() {
        services.forEach(AbstractService::stop);
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
		AbstractService service = manager.manage(type);
		if(service == null) {
			log.error("Could not create service " + type.getName() + "!");
			return false;
		}
		services.add(service);
		return true;
	}
}
