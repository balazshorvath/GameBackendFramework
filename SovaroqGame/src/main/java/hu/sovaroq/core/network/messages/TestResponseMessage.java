package hu.sovaroq.core.network.messages;

import java.io.IOException;

import hu.sovaroq.core.network.stream.GameInputStream;
import hu.sovaroq.core.network.stream.GameOutputStream;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class TestResponseMessage extends NetworkMessage {
	
    static {
        messageType = NetworkMessageType.TESTER_RES;
    }
    
    String testString1;
    
    Double testDouble1;
    
    Integer testInteger1;
    
    String testString2;
    

	@Override
	public void writeMessage(GameOutputStream outputStream) throws IOException {
		outputStream.writeStartNextMessage(messageType);
		
	}

	@Override
	public void readMessage(GameInputStream input) {
		// TODO Auto-generated method stub
		
	}
}
