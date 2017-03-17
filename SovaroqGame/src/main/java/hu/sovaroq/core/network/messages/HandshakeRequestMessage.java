package hu.sovaroq.core.network.messages;

import lombok.Data;

import java.io.OutputStream;

@Data
public class HandshakeRequestMessage extends NetworkMessage {

	static{
		messageType = NetworkMessageType.HANDSHAKE_REQ;
	}
	
	

	@Override
	public void writeMessage(OutputStream outputStream) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void parseMessage(byte[] input) {
		// TODO Auto-generated method stub
		
	}
	
}
