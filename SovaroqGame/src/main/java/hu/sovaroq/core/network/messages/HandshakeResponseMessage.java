package hu.sovaroq.core.network.messages;

import java.io.OutputStream;

public class HandshakeResponseMessage extends NetworkMessage {
	
	static{
		messageType = NetworkMessageType.HANDSHAKE_RES;
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
