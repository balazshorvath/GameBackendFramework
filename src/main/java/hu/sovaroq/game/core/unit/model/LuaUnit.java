package hu.sovaroq.game.core.unit.model;

import lombok.AllArgsConstructor;

/**
 * Created by balazs_horvath on 3/14/2017.
 */
@AllArgsConstructor
public class LuaUnit {
    public int id;
    public double x, y;
    public double speed;
    public int hp, attack;
}
