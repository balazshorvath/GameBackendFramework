package hu.sovaroq.core.network.messages;

import hu.sovaroq.core.network.stream.GameInputStream;
import hu.sovaroq.core.network.stream.GameOutputStream;

public abstract class NetworkMessage {
	
	public static String MESSAGE_START = "###";
	
	public static String DATA_DELIMITER = ";";	

    protected static NetworkMessageType messageType;

    public abstract void writeMessage(GameOutputStream outputStream);

    public abstract void parseMessage(GameInputStream input);
}
