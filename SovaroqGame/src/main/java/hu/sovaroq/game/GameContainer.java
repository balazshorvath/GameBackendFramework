package hu.sovaroq.game;

import hu.sovaroq.core.network.GameSocket;
import hu.sovaroq.framework.controller.AbstractController;
import hu.sovaroq.framework.service.AbstractService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by balazs_horvath on 3/29/2017.
 */
public class GameContainer extends AbstractController{
    List<GameController> games = new ArrayList<>();
    @Override
    public List<AbstractService> getServices() {
        return null;
    }
    void addNew(GameSocket gs, Integer gameId){
        games.stream().filter(gameController -> gameController.getGameId().equals(gameId))
                .findFirst()
                .get()
                .newConnection(gs);
    }

    class TokenService extends AbstractService {
        public void onEvent(){

        }
    }
}
