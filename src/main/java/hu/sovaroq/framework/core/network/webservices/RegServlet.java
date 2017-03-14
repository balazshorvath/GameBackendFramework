package hu.sovaroq.framework.core.network.webservices;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import hu.sovaroq.framework.core.network.webservices.AuthServlet.Request;
import hu.sovaroq.framework.core.network.webservices.AuthServlet.Response;
import hu.sovaroq.framework.service.authentication.IAuthenticationServiceEvents;
import lombok.Data;

@WebServlet(asyncSupported = true)
public class RegServlet extends HttpServlet implements IAsyncSerlvet {

	private static final long serialVersionUID = -5122656355322069145L;

	ObjectMapper objectMapper = new ObjectMapper();
	
	BlockingQueue<AsyncContext> webMessageQueue = new ArrayBlockingQueue<>(20000);
	
    public RegServlet(){}
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {    	
    	addToWaitingList(request.startAsync());
    }


    public void addToWaitingList(AsyncContext c) {
    	webMessageQueue.add(c);
    }

	@Override
	public AsyncContext poll() {
		return webMessageQueue.poll();
	}

	@Override
	public void respond(AsyncContext context, IWebResponse response) {
		try {
			objectMapper.writeValue(context.getResponse().getWriter(), response);
		} catch (IOException e) {
		}finally{
			context.complete();
		}
	}
	
	@Data
	public static class Response implements IWebResponse{
		private IAuthenticationServiceEvents.RegistrationStatus status;
	}
	
	@Data
	public static class Request implements IWebRequest{
		private String username;
		private String password;
	}
}
