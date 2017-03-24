package hu.sovaroq.core.webservices.servlet;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AbstractAsyncServlet extends HttpServlet implements IAsyncServlet{

	private static final long serialVersionUID = 4083761419727086646L;

	ObjectMapper objectMapper = new ObjectMapper();

    BlockingQueue<AsyncContext> webMessageQueue = new ArrayBlockingQueue<>(20000);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        } finally {
            context.complete();
        }
    }
	
}
