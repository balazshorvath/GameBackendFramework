package hu.sovaroq.game.core.controller;

import hu.sovaroq.framework.controller.base.AbstractController;
import hu.sovaroq.framework.controller.base.Context;
import hu.sovaroq.framework.service.base.AbstractService;
import hu.sovaroq.game.core.service.game.UnitService;

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
