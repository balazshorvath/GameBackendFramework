package hu.sovaroq.game.unit.service;

import hu.sovaroq.framework.eventing.bus.EventListener;
import hu.sovaroq.framework.service.AbstractService;
import hu.sovaroq.framework.service.Service;
import hu.sovaroq.framework.service.features.AutoSetService;
import hu.sovaroq.framework.service.features.Tick;
import hu.sovaroq.framework.scripting.LuaGlobalsProvider;
import hu.sovaroq.game.unit.model.LuaUnit;
import hu.sovaroq.game.unit.model.UnitState;
import lombok.AllArgsConstructor;
import lombok.ToString;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by balazs_horvath on 3/13/2017.
 */
@Service
@EventListener
public class UnitService extends AbstractService<Object> implements IUnitService {

    @AutoSetService
    private LuaGlobalsProvider provider;
    private UnitAPI unitAPI = new UnitAPI();
    private Globals globals;

    private AtomicInteger currentUnitId = new AtomicInteger(0);
    private Map<Integer, UnitScript> units = new ConcurrentHashMap<>();
    private long last100Tick = System.currentTimeMillis();

    @Override
    public void start(Object o) {
        log.debug(">UnitService.start()");
        super.start(o);

        globals = provider.getGlobals();
        globals.set("api", CoerceJavaToLua.coerce(unitAPI));
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

        unitAPI.flush();
    }

    public class UnitAPI {
        Set<UnitState> changes = new HashSet<>();

        public void state(int unitId){
            changes.add(new UnitState(units.get(unitId).params.self));
        }

        void flush(){
            post(new IUnitService.UnitUpdates(changes));
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
