package hu.sovaroq.core.network.messages;

public enum NetworkMessageType {

    HANDSHAKE_REQ(0),
    HANDSHAKE_RES(1),

    AES_KEY_EXCHANGE_REQ(2),
    AES_KEY_EXCHANGE_RES(3),

    AUTHENTICATE_REQ(4),
    AUTHENTICATE_RES(5);

    private int id;

    NetworkMessageType(int messageID) {
        this.id = messageID;
    }

    int id() {
        return this.id;
    }

}
