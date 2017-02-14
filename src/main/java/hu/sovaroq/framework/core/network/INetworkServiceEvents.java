package hu.sovaroq.framework.core.network;

import java.util.UUID;

import hu.sovaroq.framework.data.session.Session;
import lombok.Value;

public interface INetworkServiceEvents {

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
