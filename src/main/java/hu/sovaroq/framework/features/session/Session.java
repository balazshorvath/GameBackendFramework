package hu.sovaroq.framework.features.session;

import java.util.UUID;

import hu.sovaroq.framework.features.network.ClientConnection;
import hu.sovaroq.framework.features.user.IUser;
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
