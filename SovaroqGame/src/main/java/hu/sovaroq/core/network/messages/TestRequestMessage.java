package hu.sovaroq.core.network.messages;

import hu.sovaroq.core.network.stream.GameInputStream;
import hu.sovaroq.core.network.stream.GameOutputStream;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class TestRequestMessage extends NetworkMessage {

    static {
        messageType = NetworkMessageType.TESTER_REQ;
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
