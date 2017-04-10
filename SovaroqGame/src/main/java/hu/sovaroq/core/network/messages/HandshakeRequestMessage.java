package hu.sovaroq.core.network.messages;

import hu.sovaroq.core.network.stream.GameInputStream;
import hu.sovaroq.core.network.stream.GameOutputStream;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * This message is sent first, after socket connection accepted. This message is not yet encrypted, so avoid sending sensitive data in it. 
 * @author Horvath_Gergo
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class HandshakeRequestMessage extends NetworkMessage {

    static {
        messageType = NetworkMessageType.HANDSHAKE_REQ;
    }


    @Override
    public void writeMessage(GameOutputStream outputStream) {
        // TODO Auto-generated method stub

    }


    @Override
    public void readMessage(GameInputStream input) {
        // TODO Auto-generated method stub

    }

}
