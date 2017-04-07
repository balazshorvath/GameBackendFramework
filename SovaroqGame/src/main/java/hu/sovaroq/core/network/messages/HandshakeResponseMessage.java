package hu.sovaroq.core.network.messages;

import hu.sovaroq.core.network.stream.GameInputStream;
import hu.sovaroq.core.network.stream.GameOutputStream;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class HandshakeResponseMessage extends NetworkMessage {

    static {
        messageType = NetworkMessageType.HANDSHAKE_RES;
    }
    
    String token;

    @Override
    public void writeMessage(GameOutputStream outputStream) {
        // TODO Auto-generated method stub

    }

    @Override
    public void readMessage(GameInputStream input) {
        // TODO Auto-generated method stub

    }

}
