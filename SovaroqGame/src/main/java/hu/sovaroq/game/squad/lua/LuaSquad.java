package hu.sovaroq.game.squad.lua;

import hu.sovaroq.game.unit.model.LuaUnit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oryk on 2017. 03. 27..
 */
public class LuaSquad {
    public final byte sizeX, sizeY;

    public final List<LuaUnit> units = new ArrayList<>();
    public final WalkPath path;

    public double x, y;

    public LuaSquad(byte sizeX, byte sizeY, WalkPath path) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.path = path;
    }
}
