package hu.sovaroq.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.sovaroq.core.webservices.IWebServerEvents;
import hu.sovaroq.framework.controller.AbstractController;
import hu.sovaroq.framework.controller.Context;
import hu.sovaroq.framework.eventing.bus.IEventBus;
import hu.sovaroq.framework.service.AbstractService;
import hu.sovaroq.framework.service.manager.ServiceManager;
import hu.sovaroq.game.unit.service.UnitService;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Oryk on 2017. 02. 27..
 */
public class GameController extends AbstractController<Context> {
    private static final AtomicInteger gameCounter = new AtomicInteger(0);

    private final Integer gameId = gameCounter.getAndIncrement();
    private List<AbstractService> services = new ArrayList<>();
    private ServletContextHandler webContext;

    @Override
    public void start(Context context) {
        log.info(">GameController - start()");
        super.context = context;

        super.manager = new ServiceManager(3, 5, 5);

        if (context.isDebug()) {
            webContext = new ServletContextHandler(ServletContextHandler.SESSIONS);
            IWebServerEvents.RegisterHandlerRequest request = new IWebServerEvents.RegisterHandlerRequest(webContext);
            DebugServlet debugPort = new DebugServlet();

            String url = "/game-" + gameId;
            log.info("Registered debug port at: " + url);

            webContext.setContextPath(url);
            webContext.addServlet(new ServletHolder(debugPort), "/debug");

            webContext.destroy();
            context.getManager().getBus().pushEvent(request);
            super.manager.getBus().registerDebugPort(debugPort);
        }

        services.add(manager.manage(UnitService.class));

        log.info("<GameController - start()");
    }

    @Override
    public void stop() {
        log.info(">GameController - stop()");
        super.stop();
        if (webContext != null) {
            webContext.destroy();
        }
        log.info("<GameController - stop()");
    }

    @Override
    public List<AbstractService> getServices() {
        return services;
    }

    @WebServlet
    public static class DebugServlet extends HttpServlet implements IEventBus.IEventBusDebugPort {
        private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

        private List<Object> buffer = new ArrayList<>();
        private ObjectMapper mapper = new ObjectMapper();

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            try {
                lock.writeLock().lock();
                resp.getOutputStream().println(mapper.writeValueAsString(buffer));
                buffer.clear();
            } finally {
                lock.writeLock().unlock();
            }
        }

        @Override
        public void newEvent(Object event) {
            try {
                lock.writeLock().lock();
                buffer.add(event);
            } finally {
                lock.writeLock().unlock();
            }
        }
    }
}
