package hu.sovaroq.framework.network;

import java.util.UUID;

import hu.sovaroq.framework.session.Session;
import lombok.Value;

public interface INetworkControllerEvents {

	@Value
	public class NewClientConnectionRequest{
		UUID sessionID;
		ClientConnection connection;
	}
	
	@Value
	public class NewClientConnectionResponse{
		Session clientSession;
	}
	
}
