package hu.sovaroq.core.network.messages;

import hu.sovaroq.core.network.stream.GameInputStream;
import hu.sovaroq.core.network.stream.GameOutputStream;

public abstract class NetworkMessage {
	
	public static byte MESSAGE_START = 0x02; //start of text
	
	public static byte MESSAGE_END = 0x03; //end of text
	
	public static byte DATA_DELIMITER = 0x00; //that

    protected static NetworkMessageType messageType;

    public abstract void writeMessage(GameOutputStream outputStream);

    public abstract void readMessage(GameInputStream input);
}
