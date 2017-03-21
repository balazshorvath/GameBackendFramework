package hu.sovaroq.core.user.session;

import hu.sovaroq.core.network.ClientConnection;
import hu.sovaroq.core.user.authentication.IUser;
import hu.sovaroq.game.data.PlayerBase;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Builder
@Value
public class Session {
    UUID sessionID;
    ClientConnection clientConnection;
    PlayerBase player;
    IUser user;
}
