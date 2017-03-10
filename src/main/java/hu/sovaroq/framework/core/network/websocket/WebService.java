package hu.sovaroq.framework.core.network.websocket;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import hu.sovaroq.framework.service.base.AbstractService;

public class WebService extends AbstractService<WebService.WebServiceConfig>{

    public static void main(String[] args)
    {
        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8090);
        server.addConnector(connector);

        // Setup the basic application "context" for this application at "/"
        // This is also known as the handler tree (in jetty speak)
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/auth");
        server.setHandler(context);
        
        // Add a websocket to a specific path spec

        context.addServlet(new ServletHolder(new TestServlet()),"/kaki");
        
        try
        {
            server.start();
            server.dump(System.err);
            server.join();
        }
        catch (Throwable t)
        {
            t.printStackTrace(System.err);
        }
        
    }
    
    public class WebServiceConfig{
    	String keyStorePath;
    	int webservicePort;
    	boolean useSSL;
    	boolean debugServletEnabled;
    }
	
}
