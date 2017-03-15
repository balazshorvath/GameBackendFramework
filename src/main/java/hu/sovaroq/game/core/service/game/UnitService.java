package hu.sovaroq.game.core.service.game;

import hu.sovaroq.framework.core.bus.EventListener;
import hu.sovaroq.framework.service.base.AbstractService;
import hu.sovaroq.framework.service.base.Service;
import hu.sovaroq.framework.service.features.Tick;
import hu.sovaroq.game.core.unit.model.LuaUnit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
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
@EventListener
public class UnitService extends AbstractService<Object> implements IUnitService {
    private AtomicInteger currentUnitId = new AtomicInteger(0);
    private Globals globals;
    private Map<Integer, UnitScript> units = new ConcurrentHashMap<>();
    private long last100Tick = System.currentTimeMillis();

    @Override
    public void start(Object o) {
        log.debug(">UnitService.start()");
        super.start(o);

        globals = JsePlatform.standardGlobals();
        LuaValue helpers = globals.loadfile("game/global/helpers.lua");
        helpers.call();
        log.debug("<UnitService.start()");
    }

    public void onEvent(IUnitService.SpawnUnit spawn){
        log.debug("Got new Message: " + spawn);
        LuaValue script = globals.loadfile(spawn.getLuaScript());
        UnitParameters params = new UnitParameters(
                spawn.getTargetX(), spawn.getTargetY(),
                new LuaUnit(currentUnitId.incrementAndGet(), spawn.getX(), spawn.getY(), 10.0, 100, 10),
                0
        );
        units.put(
                currentUnitId.incrementAndGet(),
                new UnitScript(params,script)
        );
        script.call(CoerceJavaToLua.coerce(params));
    }


    @Tick(100)
    public void tick100(){
        long now = System.currentTimeMillis();
        final long dt = now - last100Tick;
        last100Tick = now;

        units.values().forEach(unit -> {
            unit.params.dt = dt;
            unit.script.call(CoerceJavaToLua.coerce(unit.params));
        });
    }

    public class UnitAPI {
        public void state(){
            // pending changes
            // finalize -> create event and send
        }
    }

    @AllArgsConstructor
    public static class UnitScript {
        UnitParameters params;
        LuaValue script;
    }

    @ToString
    @AllArgsConstructor
    public static class UnitParameters {
        public double targetX, targetY;
        public LuaUnit self;
        public long dt;
    }
}
