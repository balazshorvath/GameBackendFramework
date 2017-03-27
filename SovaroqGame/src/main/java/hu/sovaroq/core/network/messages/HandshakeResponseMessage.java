package hu.sovaroq.core.network.messages;

import hu.sovaroq.core.network.stream.GameInputStream;
import hu.sovaroq.core.network.stream.GameOutputStream;

public class HandshakeResponseMessage extends NetworkMessage {

    static {
        messageType = NetworkMessageType.HANDSHAKE_RES;
    }

    @Override
    public void writeMessage(GameOutputStream outputStream) {
        // TODO Auto-generated method stub

    }

    @Override
    public void parseMessage(GameInputStream input) {
        // TODO Auto-generated method stub

    }

}
