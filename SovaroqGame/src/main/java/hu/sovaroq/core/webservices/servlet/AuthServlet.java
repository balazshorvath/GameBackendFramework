package hu.sovaroq.core.webservices.servlet;

import java.util.UUID;

import javax.servlet.annotation.WebServlet;

import hu.sovaroq.core.user.authentication.IAuthenticationServiceEvents;
import lombok.Data;

@WebServlet(asyncSupported = true)
public class AuthServlet extends AbstractAsyncServlet {
	private static final long serialVersionUID = 5325206159172282055L;

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
