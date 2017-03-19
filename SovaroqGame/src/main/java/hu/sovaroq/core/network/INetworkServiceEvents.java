package hu.sovaroq.core.network;

import hu.sovaroq.core.user.session.Session;
import lombok.Value;

import java.util.UUID;

public interface INetworkServiceEvents {

    @Value
    public class NewClientConnectionRequest {
        UUID sessionID;
//		ClientConnection connection;
    }

    @Value
    public class NewClientConnectionResponse {
        Session clientSession;
    }

}
