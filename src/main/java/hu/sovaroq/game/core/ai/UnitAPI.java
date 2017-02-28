package hu.sovaroq.game.core.ai;

import hu.sovaroq.game.core.data.UnitBase;
import org.luaj.vm2.LuaValue;

/**
 * Created by Oryk on 2017. 02. 28..
 */
public class UnitAPI {
    protected final UnitBase base;
    protected final long unitId;
    protected final long objective;

    protected double x, y;
    protected int tileX, tileY;
    protected UnitState state;

    public UnitAPI(UnitBase base, long unitId, long objective) {
        this.base = base;
        this.unitId = unitId;
        this.objective = objective;
    }

    public LuaValue id() {
        return LuaValue.valueOf(unitId);
    }

    public LuaValue x() {
        return LuaValue.valueOf(x);
    }

    public LuaValue y() {
        return LuaValue.valueOf(y);
    }

    public LuaValue tx() {
        return LuaValue.valueOf(tileX);
    }

    public LuaValue ty() {
        return LuaValue.valueOf(tileY);
    }

    public LuaValue state() {
        return LuaValue.valueOf(state.val());
    }

    public LuaValue objective() {
        return LuaValue.valueOf(objective);
    }

    public enum UnitState {
        NO_OBJECTIVE(0),
        MOVING(1),
        FIGHTING(2);

        final int i;

        UnitState(int i) {
            this.i = i;
        }
        public int val(){
            return i;
        }
    }
}
