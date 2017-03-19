package hu.sovaroq.core.webservices;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.sovaroq.core.user.authentication.IAuthenticationServiceEvents;
import hu.sovaroq.core.webservices.servlet.IAsyncServlet;
import hu.sovaroq.core.webservices.servlet.IWebRequest;
import hu.sovaroq.core.webservices.servlet.IWebResponse;
import lombok.Data;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@WebServlet(asyncSupported = true)
public class AuthServlet extends HttpServlet implements IAsyncServlet {
    private static final long serialVersionUID = 6715948539238549394L;

    ObjectMapper objectMapper = new ObjectMapper();

    BlockingQueue<AsyncContext> webMessageQueue = new ArrayBlockingQueue<>(20000);

    public AuthServlet() {
    }

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

    @Data
    public static class Response implements IWebResponse {
        private IAuthenticationServiceEvents.AuthenticationStatus status;
        private UUID sessionID;
    }

    @Data
    public static class Request implements IWebRequest {
        private String username;
        private String password;
    }
}