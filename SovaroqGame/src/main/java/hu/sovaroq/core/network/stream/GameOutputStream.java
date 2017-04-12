package hu.sovaroq.core.network.stream;

import java.io.IOException;
import java.io.OutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;

import hu.sovaroq.core.network.messages.NetworkMessage;
import hu.sovaroq.core.network.messages.NetworkMessageType;

public class GameOutputStream {
	
	OutputStream outputs;

	public GameOutputStream(OutputStream os) {
		outputs = os;
	}
	
	public void writeString(String string) throws IOException {
		outputs.write(string.getBytes(NetworkMessage.defaultCharset));
		outputs.write(NetworkMessage.DATA_DELIMITER);
	}

	public void writeStartNextMessage(NetworkMessageType messageType) throws IOException {
		outputs.write(NetworkMessage.MESSAGE_START);
		outputs.write(messageType.id());
	}
	
	public void writeFinishMessage() throws IOException{
		outputs.write(NetworkMessage.MESSAGE_END);
	}

	public void writeDouble(Double number) throws IOException {
		outputs.write(number.toString().getBytes(NetworkMessage.defaultCharset));
		outputs.write(NetworkMessage.DATA_DELIMITER);
	}

	public void writeInt(Integer number) throws IOException {
		outputs.write(number.toString().getBytes(NetworkMessage.defaultCharset));
		outputs.write(NetworkMessage.DATA_DELIMITER);
	}
	
	public void close() throws IOException{
		outputs.close();
	}

}
