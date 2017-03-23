package hu.sovaroq.core.webservices;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerCollection;

import hu.sovaroq.framework.service.AbstractService;

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
			log.error("Enable to start webserver" + e);
		}
    }
    
    public void onEvent(IWebServerEvents.RegisterHandlerRequest request){
    	this.handlers.addHandler(request.getHandler());
    }
    
    public class WebServerConfig{
    	int webServerPort;
		String keyStorePath;
		boolean useSSL;
		boolean debugServletEnabled;
    }

}
