package hu.sovaroq.core.network.stream;

import java.io.IOException;
import java.io.OutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;

import hu.sovaroq.core.network.messages.NetworkMessage;
import hu.sovaroq.core.network.messages.NetworkMessageType;

public class GameOutputStream extends CipherOutputStream{

	public GameOutputStream(OutputStream os, Cipher c) {
		super(os, c);
	}
	
	public void writeString(String string) throws IOException {
		write(string.getBytes(NetworkMessage.defaultCharset));
		write(NetworkMessage.DATA_DELIMITER);
	}

	public void writeStartNextMessage(NetworkMessageType messageType) throws IOException {
		write(NetworkMessage.MESSAGE_START);
		write(messageType.id());
	}
	
	public void writeFinishMessage() throws IOException{
		write(NetworkMessage.MESSAGE_END);
	}

	public void writeDouble(Double number) throws IOException {
		write(number.toString().getBytes(NetworkMessage.defaultCharset));
		write(NetworkMessage.DATA_DELIMITER);
	}

	public void writeInt(Integer number) throws IOException {
		write(number.toString().getBytes(NetworkMessage.defaultCharset));
		write(NetworkMessage.DATA_DELIMITER);
	}

}
