package hu.sovaroq.core.webservices.servlet;

import java.util.UUID;

import javax.servlet.annotation.WebServlet;

import hu.sovaroq.core.user.account.IUserAccount;
import lombok.Data;

@WebServlet(asyncSupported = true)
public class AccountDetailsServlet extends AbstractAsyncServlet {
	private static final long serialVersionUID = -7215787849770477673L;

	@Data
    public static class Response implements IWebResponse {
		private UUID sessionID;
		private IUserAccount account;
    }

    @Data
    public static class Request implements IWebRequest {
    	private UUID sessionID;
    }
	
}
