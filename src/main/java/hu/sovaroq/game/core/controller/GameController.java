package hu.sovaroq.game.core.controller;

import hu.sovaroq.framework.controller.base.AbstractController;
import hu.sovaroq.framework.controller.base.Context;
import hu.sovaroq.framework.core.command.FrameworkCommand;
import hu.sovaroq.framework.service.base.AbstractService;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

/**
 * Created by Oryk on 2017. 02. 27..
 */
public class GameController extends AbstractController<Context> {
    @Override
    public void start(Context context) {
        super.start(context);

        Globals globals = JsePlatform.standardGlobals();
        LuaValue chunk = null;
        try {
            chunk = globals.load(new FileReader("game/unit.lua"), "building");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        chunk.call();
    }

    @Override
    public void stop() {
        super.stop();
    }

    @Override
    public List<AbstractService> getServices() {
        return null;
    }

    public static class ScriptContext {

    }
}
