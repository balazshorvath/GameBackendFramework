package hu.sovaroq.core.network.messages;

import hu.sovaroq.core.network.stream.GameInputStream;
import hu.sovaroq.core.network.stream.GameOutputStream;
import lombok.Data;

@Data
public class HandshakeRequestMessage extends NetworkMessage {

    static {
        messageType = NetworkMessageType.HANDSHAKE_REQ;
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
