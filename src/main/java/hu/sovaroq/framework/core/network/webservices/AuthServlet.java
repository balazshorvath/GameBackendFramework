package hu.sovaroq.framework.core.network.webservices;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(asyncSupported = true)
public class AuthServlet extends HttpServlet implements IAsyncSerlvet
{
	private static final long serialVersionUID = 6715948539238549394L;
	
	BlockingQueue<AsyncContext> webMessageQueue = new ArrayBlockingQueue<>(20000);
	
    public AuthServlet(){}
    
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
	public void respond(AsyncContext context) {
		try {
			context.getResponse().getWriter().println("OK\r\n");
		} catch (IOException e) {
		}finally{
			context.complete();
		}
	}
    
}
