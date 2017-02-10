package hu.sovaroq.framework.network;

import java.util.UUID;

import hu.sovaroq.framework.session.Session;
import lombok.Builder;
import lombok.Data;
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
