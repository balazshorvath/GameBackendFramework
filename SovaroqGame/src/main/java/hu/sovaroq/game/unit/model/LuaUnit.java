package hu.sovaroq.game.unit.model;

import hu.sovaroq.game.data.modifiers.Modifier;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by balazs_horvath on 3/14/2017.
 */
public class LuaUnit {
    public final int id;
    public final int squadX, squadY;
    public double x, y;
    public final List<Modifier> modifiers = new ArrayList<>();

    public LuaUnit(int id, int squadX, int squadY) {
        this.id = id;
        this.squadX = squadX;
        this.squadY = squadY;
    }
}
