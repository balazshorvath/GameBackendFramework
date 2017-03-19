package hu.sovaroq.game;

import hu.sovaroq.core.webservices.servlet.IAsyncServlet;
import hu.sovaroq.core.webservices.servlet.IWebResponse;
import hu.sovaroq.framework.controller.AbstractController;
import hu.sovaroq.framework.controller.Context;
import hu.sovaroq.framework.eventing.bus.IEventBus;
import hu.sovaroq.framework.service.AbstractService;
import hu.sovaroq.framework.service.manager.ServiceManager;
import hu.sovaroq.game.unit.service.UnitService;

import javax.servlet.AsyncContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oryk on 2017. 02. 27..
 */
public class GameController extends AbstractController<Context> {
    private List<AbstractService> services = new ArrayList<>();
    private IEventBus.IEventBusDebugPort debugPort;

    @Override
    public void start(Context context) {
        super.context = context;

        super.manager = new ServiceManager(3, 5, 5);

        services.add(manager.manage(UnitService.class));
    }

    @Override
    public void stop() {
        super.stop();
    }

    @Override
    public List<AbstractService> getServices() {
        return services;
    }

    @WebServlet
    public static class DebugServlet extends HttpServlet implements IAsyncServlet {

        @Override
        public AsyncContext poll() {
            return null;
        }

        @Override
        public void respond(AsyncContext context, IWebResponse response) {

        }
    }
}
