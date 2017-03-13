package hu.sovaroq.framework.core.network.webservices;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.servlet.AsyncContext;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import hu.sovaroq.framework.service.base.AbstractService;
import hu.sovaroq.framework.service.features.Run;

public class CoreWebService extends AbstractService<CoreWebService.WebServiceConfig>{
	
	ServletContextHandler coreContext = new ServletContextHandler(ServletContextHandler.SESSIONS);
	
	boolean enabled;
	
	private final BlockingQueue<AsyncContext> webMessageQueue = new ArrayBlockingQueue<>(20000);
    
    @Override
    public void start(WebServiceConfig config) {
    	log.debug("Starting CoreWebService");
    	super.start(config);

    	coreContext.setContextPath("/auth");
    	coreContext.addServlet(new ServletHolder(new AuthServlet()),"/");
        WebServer.setHandler(coreContext);      

        enabled = true;
    }
    
    @Run
    public void run(){
    	while(enabled){
    		
    	}
    }
    
    @Override
    public void stop() {
    	enabled = false;
    	log.debug("Stopping CoreWebService");
    }
    
    public class WebServiceConfig{
    	String keyStorePath;
    	boolean useSSL;
    	boolean debugServletEnabled;
    }
	
}
