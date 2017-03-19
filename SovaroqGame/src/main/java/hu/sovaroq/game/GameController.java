package hu.sovaroq.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.sovaroq.framework.controller.AbstractController;
import hu.sovaroq.framework.controller.Context;
import hu.sovaroq.framework.eventing.bus.IEventBus;
import hu.sovaroq.framework.service.AbstractService;
import hu.sovaroq.framework.service.manager.ServiceManager;
import hu.sovaroq.game.unit.service.UnitService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

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

        if (context.isDebug()) {
            super.manager.getBus().registerDebugPort(new DebugServlet());
        }

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
    public static class DebugServlet extends HttpServlet implements IEventBus.IEventBusDebugPort {
        private Queue<Object> queue = new ConcurrentLinkedQueue<>();
        private ObjectMapper mapper = new ObjectMapper();

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        }

        @Override
        public void newEvent(Object event) {
            queue.add(event);
        }
    }
}
