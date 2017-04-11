package hu.sovaroq.core.network.messages;

import java.io.IOException;
import java.nio.charset.Charset;

import hu.sovaroq.core.network.stream.GameInputStream;
import hu.sovaroq.core.network.stream.GameOutputStream;

public abstract class NetworkMessage {
	
	public static byte MESSAGE_START = 0x02; //start of text
	
	public static byte MESSAGE_END = 0x03; //end of text
	
	public static byte DATA_DELIMITER = 0x00; //that
	
	public static final Charset defaultCharset = Charset.forName("ASCII"); 

    protected static NetworkMessageType messageType;

    public abstract void writeMessage(GameOutputStream outputStream) throws IOException;

    public abstract void readMessage(GameInputStream input) throws IOException;
}
