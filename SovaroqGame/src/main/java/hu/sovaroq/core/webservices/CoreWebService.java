package hu.sovaroq.core.webservices;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.AsyncContext;
import javax.servlet.Servlet;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.fasterxml.jackson.databind.ObjectMapper;

import hu.sovaroq.core.user.authentication.IAuthenticationServiceEvents;
import hu.sovaroq.core.webservices.servlet.AccountDetailsServlet;
import hu.sovaroq.core.webservices.servlet.AuthServlet;
import hu.sovaroq.core.webservices.servlet.IAsyncServlet;
import hu.sovaroq.core.webservices.servlet.RegServlet;
import hu.sovaroq.framework.eventing.bus.EventListener;
import hu.sovaroq.framework.service.AbstractService;
import hu.sovaroq.framework.service.Service;
import hu.sovaroq.framework.service.features.Run;

/**
 * Responsible for handling all web servlets serving the core functionality
 *
 * @author Horvath_Gergo
 */
@Service
@EventListener
public class CoreWebService extends AbstractService<CoreWebService.WebServiceConfig> {

	private ServletContextHandler coreContext = new ServletContextHandler(ServletContextHandler.SESSIONS);
	private Map<Long, AsyncContext> frameworkRequestToContext = new ConcurrentHashMap<Long, AsyncContext>();

	private IAsyncServlet authServlet = new AuthServlet();
	private IAsyncServlet regServlet = new RegServlet();
	private IAsyncServlet accDetailsServlet = new AccountDetailsServlet();

	private boolean enabled;

    @Override
    public void start(WebServiceConfig config) {
		log.debug("Starting CoreWebService");
		super.start(config);

		coreContext.setContextPath("/auth");
		coreContext.addServlet(new ServletHolder((Servlet) authServlet), "/login");
		coreContext.addServlet(new ServletHolder((Servlet) regServlet), "/register");
		coreContext.addServlet(new ServletHolder((Servlet) accDetailsServlet), "/account");

		post(new IWebServerEvents.RegisterHandlerRequest(coreContext));

        enabled = true;
    }

    @Run
	public void run() {
		log.debug("Webservice polling started.");
		AsyncContext context = null;
		ObjectMapper objectMapper = new ObjectMapper();
		while (enabled) {
			context = authServlet.poll();
			if (context != null) {
				try {
					log.debug("Received http request: " + context.toString());
					AuthServlet.Request request = objectMapper.readValue(context.getRequest().getReader(), AuthServlet.Request.class);
					IAuthenticationServiceEvents.AuthenticationRequest authenticationRequest = new IAuthenticationServiceEvents.AuthenticationRequest(request.getUsername(), request.getPassword());
					frameworkRequestToContext.put(authenticationRequest.getRequestId(), context);
					post(authenticationRequest);
				} catch (IOException e) {
					
					log.error("Failed to parse auth package: ", e);
				}
			}
			context = regServlet.poll();
			if (context != null) {
				try {
					log.debug("Received http request: " + context.toString());
					RegServlet.Request request = objectMapper.readValue(context.getRequest().getReader(), RegServlet.Request.class);
					IAuthenticationServiceEvents.RegisterUserRequest regRequest = new IAuthenticationServiceEvents.RegisterUserRequest(request.getUsername(), request.getEmail(), request.getPassword());
					frameworkRequestToContext.put(regRequest.getRequestId(), context);
					post(regRequest);
				} catch (IOException e) {
					log.error("Failed to parse auth package: ", e);
				}
			}
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
			}
		}
	}

	public void onEvent(IAuthenticationServiceEvents.AuthenticationSuccessResponse response) {
		log.debug("received: " + response);

		AsyncContext asyncContext = frameworkRequestToContext.get(response.getRequestId());
		if (asyncContext != null) {
			AuthServlet.Response webResp = new AuthServlet.Response();
			webResp.setSessionID(response.getSession().getSessionID());
			webResp.setStatus(IAuthenticationServiceEvents.AuthenticationStatus.success);
			authServlet.respond(asyncContext, webResp);
		}
	}

	public void onEvent(IAuthenticationServiceEvents.AuthenticationFailureResponse response) {
		log.debug("received: " + response);

		AsyncContext asyncContext = frameworkRequestToContext.get(response.getRequestId());
		if (asyncContext != null) {
			AuthServlet.Response webResp = new AuthServlet.Response();
			webResp.setStatus(response.getStatus());
			authServlet.respond(asyncContext, webResp);
		}
	}

	public void onEvent(IAuthenticationServiceEvents.RegisterUserSuccessResponse response) {
		log.debug("received: " + response);

		AsyncContext asyncContext = frameworkRequestToContext.get(response.getRequestId());
		if (asyncContext != null) {
			RegServlet.Response webResp = new RegServlet.Response();
			webResp.setStatus(IAuthenticationServiceEvents.RegistrationStatus.success);
			authServlet.respond(asyncContext, webResp);
		}
	}

	public void onEvent(IAuthenticationServiceEvents.RegisterUserFailureResponse response) {
		log.debug("received: " + response);

		AsyncContext asyncContext = frameworkRequestToContext.get(response.getRequestId());
		if (asyncContext != null) {
			RegServlet.Response webResp = new RegServlet.Response();
			webResp.setStatus(response.getStatus());
			authServlet.respond(asyncContext, webResp);
		}
	}

    @Override
    public void stop() {
		enabled = false;
		log.debug("Stopping CoreWebService");
		try {
			coreContext.stop();
		} catch (Exception e) {
			log.error("Failed to stop coreContext" + e);
		}
	}

	public class WebServiceConfig {
	}

}
