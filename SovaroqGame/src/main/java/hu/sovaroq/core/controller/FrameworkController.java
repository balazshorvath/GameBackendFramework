package hu.sovaroq.core.controller;

import hu.sovaroq.core.database.service.HibernateDatabaseService;
import hu.sovaroq.core.user.authentication.AuthenticationService;
import hu.sovaroq.core.user.session.SessionService;
import hu.sovaroq.core.webservices.CoreWebService;
import hu.sovaroq.core.webservices.WebServer;
import hu.sovaroq.framework.controller.AbstractController;
import hu.sovaroq.framework.controller.Context;
import hu.sovaroq.framework.scripting.LuaGlobalsProvider;
import hu.sovaroq.framework.service.AbstractService;

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
        register(AuthenticationService.class);
        register(CoreWebService.class);
        register(WebServer.class);
        register(SessionService.class);
        register(LuaGlobalsProvider.class);

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

    private boolean register(Class<? extends AbstractService> type) {
        log.info("FrameworkController - register(" + type.getName() + ")");
        AbstractService service = manager.manage(type);
        if (service == null) {
            log.error("Could not create service " + type.getName() + "!");
            return false;
        }
        services.add(service);
        return true;
    }
}
