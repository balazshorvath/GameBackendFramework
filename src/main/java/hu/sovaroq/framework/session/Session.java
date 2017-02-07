package hu.sovaroq.framework.session;

import java.util.UUID;

import hu.sovaroq.framework.network.ClientConnection;
import hu.sovaroq.framework.user.IUser;
import hu.sovaroq.game.core.base.PlayerBase;
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
