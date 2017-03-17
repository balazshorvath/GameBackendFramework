package hu.sovaroq.core.webservices.servlet;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class WebServer {

	private static Server server = null;
	
	private final static int webServerPort = 9713;
	
	private static boolean stopping = false;
	
	//TODO: maybe we need multiple ports later, than use this pattern with setHandler(ServletContextHandler handler, int port), and make a list of ports+strings: 
//    ServerConnector httpConnector = new ServerConnector(server);
//    httpConnector.setName("unsecured"); // named connector
//    httpConnector.setPort(80);
//
//    ContextHandler helloHandler = new ContextHandler();
//    helloHandler.setContextPath("/hello");
//    helloHandler.setHandler(new HelloHandler("Hello World"));
//    helloHandler.setVirtualHosts(new String[]{"@unsecured"});
	
	public static void setHandler(ServletContextHandler handler) {
		if (server == null) {
			server = new Server();
			ServerConnector connector = new ServerConnector(server);
			connector.setPort(webServerPort);
			server.addConnector(connector);
		}
		if (server.isRunning()) {
			try {
				server.stop();
			} catch (Exception e) {
			}
		}
		server.setHandler(handler);
		if(server.isStopped()){
			try {
				server.start();
			} catch (Exception e) {
			}
		}
	}
	
	public static void stop(){
		if(stopping) return;
		stopping = true;
		try {
			server.stop();
		} catch (Exception e) {
		}
	}
}
