package hu.sovaroq.framework.scripting;

import hu.sovaroq.framework.service.AbstractService;
import hu.sovaroq.framework.service.Service;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LoadState;
import org.luaj.vm2.compiler.LuaC;
import org.luaj.vm2.lib.*;
import org.luaj.vm2.lib.jse.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Oryk on 2017. 03. 15..
 */
@Service
public class LuaGlobalsProvider extends AbstractService<Object>{
    private Map<String, Globals> globals = new ConcurrentHashMap<>();


    public Globals getGlobals(){
        return getGlobals(null);
    }

    /**
     * Creates a new lua globals instance if:
     *      - the param is null, or empty string,
     *      - there's no globals with the param saved.
     *
     * Returns the global, if the global with the name is already present.
     *
     * @param globalsName
     * @return
     */
    public Globals getGlobals(String globalsName){
        if(globalsName == null ||globalsName.isEmpty()){
            return createGlobals();
        }

        if(globals.containsKey(globalsName)){
            return globals.get(globalsName);
        }
        Globals g = createGlobals();
        globals.put(globalsName, g);
        return g;
    }

    public void removeGlobals(String globalsName){
        globals.remove(globalsName);
    }

    private Globals createGlobals(){
        Globals globals = new Globals();
        globals.load(new JseBaseLib());
        globals.load(new PackageLib());
        globals.load(new Bit32Lib());
        globals.load(new TableLib());
        globals.load(new StringLib());
        globals.load(new CoroutineLib());
        globals.load(new JseMathLib());
        globals.load(new JseIoLib());
        globals.load(new JseOsLib());
        globals.load(new SovaroqLuaJavaLib());

        LoadState.install(globals);
        LuaC.install(globals);

        return globals;
    }

    public static final class SovaroqLuaJavaLib extends LuajavaLib {
        public SovaroqLuaJavaLib() {}
        protected Class classForName(String name) throws ClassNotFoundException {
            // Use plain class loader in applets.
            return Class.forName(name);
        }
    }
}
