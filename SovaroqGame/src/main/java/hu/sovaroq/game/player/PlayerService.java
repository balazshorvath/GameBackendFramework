package hu.sovaroq.game.player;

import hu.sovaroq.core.network.ClientConnection;
import hu.sovaroq.framework.eventing.bus.EventListener;
import hu.sovaroq.framework.service.AbstractService;
import hu.sovaroq.framework.service.Service;
import hu.sovaroq.framework.service.features.Run;

import java.util.Set;

/**
 * Created by balazs_horvath on 3/27/2017.
 */
@Service
@EventListener
public class PlayerService extends AbstractService {
    //@AutoSetService
    // GameService game;
    Set<ClientStatus> players;

    public void onEvent(ClientConnected c){
        //
    }
    public void onEvent(UnitStatusUpdate c){
        // Kiküldi, akinek kell
    }

    @Run
    public void pollClients(){
//        for (ClientStatus player : players) {
//            player.connection.poll();
//            post(polledEvent);
//        }
    }

    public static class ClientConnected {
        ClientConnection connection;
    }
    public static class UnitStatusUpdate {
    }
    public static class ClientStatus {
        ClientConnection connection;
        double loadingPercentage;
    }
}
