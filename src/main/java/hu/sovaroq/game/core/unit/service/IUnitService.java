package hu.sovaroq.game.core.unit.service;

import hu.sovaroq.framework.core.eventbase.FrameworkInformEvent;
import hu.sovaroq.framework.core.eventbase.FrameworkRequestEvent;
import hu.sovaroq.game.core.unit.model.UnitState;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.util.Set;

/**
 * Created by balazs_horvath on 3/13/2017.
 */
public interface IUnitService {

    @Value
    @ToString
    @EqualsAndHashCode(callSuper = true)
    class SpawnUnit extends FrameworkRequestEvent {
        private String luaScript;
        private double x, y;
        private double targetX, targetY;
    }

    @Value
    @ToString
    @EqualsAndHashCode(callSuper = true)
    class UnitUpdates extends FrameworkInformEvent{
        private Set<UnitState> updates;
    }
}
