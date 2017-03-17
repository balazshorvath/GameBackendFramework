package hu.sovaroq.core.network;

import java.util.UUID;

import hu.sovaroq.core.user.session.Session;
import lombok.Value;

public interface INetworkServiceEvents {

	@Value
	public class NewClientConnectionRequest{
		UUID sessionID;
//		ClientConnection connection;
	}
	
	@Value
	public class NewClientConnectionResponse{
		Session clientSession;
	}
	
}
