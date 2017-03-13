package hu.sovaroq.game.core.service.game;

import hu.sovaroq.framework.service.base.AbstractService;
import hu.sovaroq.framework.service.base.Service;
import hu.sovaroq.framework.service.features.Tick;
import hu.sovaroq.game.core.ai.UnitAPI;
import lombok.AllArgsConstructor;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by balazs_horvath on 3/13/2017.
 */
@Service
public class UnitService extends AbstractService<Object> {
    AtomicInteger currentUnitId = new AtomicInteger(1);
    Globals globals;
    Map<Integer, UnitScript> units = new ConcurrentHashMap<>();

    @Override
    public void start(Object o) {
        super.start(o);

        Globals globals = JsePlatform.standardGlobals();
        LuaValue helpers = globals.loadfile("game/global/helpers.lua");

        helpers.call();
    }

    public void onEvent(IUnitService.SpawnUnit spawn){
        units.put(
                currentUnitId.incrementAndGet(),
                new UnitScript(
                    CoerceJavaToLua.coerce(new UnitParameters(
                            spawn.getTargetX(), spawn.getTargetY(),
                            new UnitAPI(spawn.getX(), spawn.getY()))
                    ),
                    globals.loadfile(spawn.getLuaScript())
                )
        );
    }


    @Tick(100)
    public void tick100(){
        units.values().forEach(unit -> unit.script.call(CoerceJavaToLua.coerce(unit.params)));
    }

    @AllArgsConstructor
    public static class UnitScript {
        LuaValue params;
        LuaValue script;
    }

    @AllArgsConstructor
    public static class UnitParameters {
        public double targetX, targetY;
        public UnitAPI self;
    }
}
