package hu.sovaroq.game.scenario;

import hu.sovaroq.framework.service.AbstractService;
import hu.sovaroq.framework.service.Service;
import lombok.Data;

/**
 * Created by Oryk on 2017. 03. 19..
 */
@Service(configurationClass = GameManager.GameManagerConfig.class, configurationFile = "game/scenario.properties")
public class GameManager extends AbstractService<GameManager.GameManagerConfig> {


    @Data
    public static class GameManagerConfig {
        private String scriptFile;
    }

    public class ManagerAPI {
        public void post(Object o) {
            GameManager.this.post(o);
        }
    }
}
