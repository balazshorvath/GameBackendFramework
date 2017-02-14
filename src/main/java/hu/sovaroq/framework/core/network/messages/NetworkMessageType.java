package hu.sovaroq.framework.core.network.messages;

public enum NetworkMessageType {

	HANDSHAKE_REQ(0),
	HANDSHAKE_RES(1),
	
	AES_KEY_EXCHANGE_REQ(2),
	AES_KEY_EXCHANGE_RES(3),
	
	AUTHENTICATE_REQ(4),
	AUTHENTICATE_RES(5);
	
	NetworkMessageType(int messageID){
		this.id = messageID;
	}
	
	private int id;
	
	int id(){
		return this.id;
	}
	
}
