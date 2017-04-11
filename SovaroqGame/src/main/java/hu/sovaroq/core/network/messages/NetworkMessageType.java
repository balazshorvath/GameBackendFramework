package hu.sovaroq.core.network.messages;

public enum NetworkMessageType {

    HANDSHAKE_REQ(0),
    HANDSHAKE_RES(1),

    AES_KEY_EXCHANGE_REQ(2),
    AES_KEY_EXCHANGE_RES(3),

    AUTHENTICATE_REQ(4),
    AUTHENTICATE_RES(5),
	
	TESTER_REQ(1000),
	TESTER_RES(1001);

    private int id;

    NetworkMessageType(int messageID) {
        this.id = messageID;
    }

    public int id() {
        return this.id;
    }

}
