package hu.sovaroq.core.network;

import java.util.UUID;

/**
 * Created by balazs_horvath on 3/17/2017.
 * 
 * ClientConnection is a wrapper for GameSocket for players to connect to. It parses NetworkMessages to requests to post onto the bus.
 */
public class ClientConnection {
	
	GameSocket playerSocket;
	
	UUID sessionId;
	
	
}
