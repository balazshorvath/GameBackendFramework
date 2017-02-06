package hu.sovaroq.framework.network;

import lombok.Value;

public interface INetworkControllerMessages {

	@Value
	public class newClientConnectionRequest{
		int requestID;
		ClientConnection connection;
	}
	
	@Value
	public class newClientConnectionResponse{
		int requestID;
		ClientConnection connection;
	}
	
}
