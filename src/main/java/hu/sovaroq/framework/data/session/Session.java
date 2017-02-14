package hu.sovaroq.framework.data.session;

import java.util.UUID;

import hu.sovaroq.framework.core.network.ClientConnection;
import hu.sovaroq.framework.data.user.IUser;
import hu.sovaroq.game.core.data.PlayerBase;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class Session {	
	UUID sessionID;
	ClientConnection clientConnection;
	PlayerBase player;
	IUser user;
}
