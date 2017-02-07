package hu.sovaroq.framework.network;

import java.util.UUID;

import hu.sovaroq.framework.session.Session;
import lombok.Builder;
import lombok.Data;

public interface INetworkControllerEvents {

	@Builder
	@Data
	public class NewClientConnectionRequest{
		UUID sessionID;
		ClientConnection connection;
	}
	
	@Builder
	@Data
	public class NewClientConnectionResponse{
		Session clientSession;
	}
	
}
