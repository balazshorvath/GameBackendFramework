package hu.sovaroq.framework.core.network.messages;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

public class MessageParser {
	
	private Map<NetworkMessageType, Class<? extends NetworkMessage>> messageTypes = null;
	
	public NetworkMessage parse(byte[] message){
		if(messageTypes == null){
			loadMessageTypes();
		}
		NetworkMessage parsedMessage = null;
		
		
		
		return parsedMessage;		
	}

	private void loadMessageTypes() {
		
		Reflections ref = new Reflections("hu.sovaroq.framework.network");
		
		Set<Class<? extends NetworkMessage>> messageClasses = ref.getSubTypesOf(NetworkMessage.class);
		
		for(Class<? extends NetworkMessage> mc : messageClasses){
			try {
				Field f = mc.getDeclaredField("messageType");
				f.setAccessible(true);
				String result = (String) f.get(null);
				messageTypes.put(NetworkMessageType.HANDSHAKE_REQ, mc);
			} catch (Exception e) {
				
			}
		}
		
	}

}
