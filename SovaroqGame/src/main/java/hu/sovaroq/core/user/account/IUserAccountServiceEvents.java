package hu.sovaroq.core.user.account;

import hu.sovaroq.core.user.authentication.IAuthenticationServiceEvents.AuthenticationStatus;
import hu.sovaroq.core.user.session.Session;
import hu.sovaroq.framework.eventing.events.FrameworkRequestEvent;
import hu.sovaroq.framework.eventing.events.FrameworkResponseEvent;
import lombok.EqualsAndHashCode;
import lombok.Value;

public interface IUserAccountServiceEvents {

    @Value
    @EqualsAndHashCode(callSuper = true)
    public class UserAccountRequest extends FrameworkRequestEvent {
        String sessionID;
    }
    
    @Value
    @EqualsAndHashCode(callSuper = true)
    public class UserAccountSuccessResponse extends FrameworkResponseEvent {
        Long requestId;
        Session session;
    }

    @Value
    @EqualsAndHashCode(callSuper = true)
    public class UserAccountFailureResponse extends FrameworkResponseEvent {
        Long requestId;
        AuthenticationStatus status;
    }
	
}
