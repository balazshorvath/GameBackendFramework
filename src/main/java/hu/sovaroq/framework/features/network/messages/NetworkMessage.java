package hu.sovaroq.framework.features.network.messages;

import java.io.OutputStream;

public abstract class NetworkMessage {

	protected static NetworkMessageType messageType;
	
	public abstract void writeMessage(OutputStream outputStream);
	
	public abstract void parseMessage(byte[] input);	
}
