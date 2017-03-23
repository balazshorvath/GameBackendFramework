package hu.sovaroq.core.webservices;

import hu.sovaroq.framework.eventing.bus.EventListener;
import hu.sovaroq.framework.service.AbstractService;
import hu.sovaroq.framework.service.Service;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerCollection;

@Service
@EventListener
public class WebServer extends AbstractService<WebServer.WebServerConfig> {

    private int webServerPort = 9713;
    private Server server = null;
    HandlerCollection handlers = new HandlerCollection(true);
    
    @Override
    public void start(WebServerConfig config) {
    	super.start(config);
        server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(webServerPort);
        server.addConnector(connector);
        server.setHandler(handlers);
        try {
			server.start();
		} catch (Exception e) {
            log.error("Unable to start webserver", e);
        }
    }
    
    public void onEvent(IWebServerEvents.RegisterHandlerRequest request){
        if (server.isRunning()) {
            this.handlers.addHandler(request.getHandler());
            log.info("Registered new handler: " + request.getHandler());
        } else {
            log.error("Web server is not running, could not register handler! " + request);
        }
    }

    @Override
    public void stop() {
        super.stop();
        try {
            server.stop();
        } catch (Exception e) {
            log.error("Failed to properly stop web server.", e);
        }
    }

    public class WebServerConfig{
    	int webServerPort;
		String keyStorePath;
		boolean useSSL;
		boolean debugServletEnabled;
    }

}
