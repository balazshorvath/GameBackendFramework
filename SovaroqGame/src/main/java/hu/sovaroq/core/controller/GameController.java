package hu.sovaroq.core.controller;

import hu.sovaroq.framework.controller.AbstractController;
import hu.sovaroq.framework.controller.Context;
import hu.sovaroq.framework.service.AbstractService;
import hu.sovaroq.game.unit.service.UnitService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oryk on 2017. 02. 27..
 */
public class GameController extends AbstractController<Context> {
    List<AbstractService> services = new ArrayList<>();

    @Override
    public void start(Context context) {
        super.start(context);
        services.add(manager.manage(UnitService.class));
    }

    @Override
    public void stop() {
        super.stop();
    }

    @Override
    public List<AbstractService> getServices() {
        return services;
    }

    public static class ScriptContext {

    }
}
