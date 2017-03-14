package hu.sovaroq.game.core.service.game;

import hu.sovaroq.framework.core.eventbase.FrameworkRequestEvent;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

/**
 * Created by balazs_horvath on 3/13/2017.
 */
public interface IUnitService {

    @Value
    @EqualsAndHashCode(callSuper = true)
    @ToString
    static class SpawnUnit extends FrameworkRequestEvent {
        private String luaScript;
        private double x, y;
        private double targetX, targetY;
    }
}
