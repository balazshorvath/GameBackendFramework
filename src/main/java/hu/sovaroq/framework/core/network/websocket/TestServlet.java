package hu.sovaroq.framework.core.network.websocket;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(asyncSupported = true)
public class TestServlet extends HttpServlet
{
	Map<HttpSession, HttpServletResponse> map;
	
    private String greeting="Hello World";
    public TestServlet(){}
    public TestServlet(String greeting)
    {
        this.greeting=greeting;
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    	addToWaitingList(request.startAsync());
    }
    
    private static final BlockingQueue queue = new ArrayBlockingQueue<>(20000);

    public static void addToWaitingList(AsyncContext c) {
      queue.add(c);
    }
    
}
