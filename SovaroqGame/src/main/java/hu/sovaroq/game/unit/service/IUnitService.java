package hu.sovaroq.game.unit.service;

import hu.sovaroq.framework.eventing.events.FrameworkInformEvent;
import hu.sovaroq.framework.eventing.events.FrameworkRequestEvent;
import hu.sovaroq.game.unit.model.UnitState;
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
    class UnitUpdates extends FrameworkInformEvent {
        private Set<UnitState> updates;
    }
}
