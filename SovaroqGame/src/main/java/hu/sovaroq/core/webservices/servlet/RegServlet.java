package hu.sovaroq.core.webservices.servlet;

import javax.servlet.annotation.WebServlet;

import hu.sovaroq.core.user.authentication.IAuthenticationServiceEvents;
import lombok.Data;

@WebServlet(asyncSupported = true)
public class RegServlet extends AbstractAsyncServlet {
	private static final long serialVersionUID = 214300385460611982L;

	@Data
    public static class Response implements IWebResponse {
        private IAuthenticationServiceEvents.RegistrationStatus status;
    }

    @Data
    public static class Request implements IWebRequest {
        private String username;
        private String email;
        private String password;
    }
}
