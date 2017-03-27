package hu.sovaroq.game.unit.model;

import lombok.Value;

/**
 * Created by Oryk on 2017. 03. 15..
 */
@Value
public class UnitState {
    private final int id;
    private final double x, y;

    public UnitState(LuaUnit unit) {
        this.id = unit.id;
        this.x = unit.x;
        this.y = unit.y;
    }
}
