package hu.sovaroq.framework.console;

import hu.sovaroq.framework.core.Framework;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.LuajavaLib;

/**
 * Created by balazs_horvath on 3/2/2017.
 */
public class framework {
    public final Framework framework;

    public framework(Framework framework) {
        this.framework = framework;
    }

    public void print(){
        System.out.println("print called");
    }

    public LuaValue framework(){
        return CoerceJavaToLua.coerce(framework);
    }
    public static final class ConsoleLuajavaLib extends LuajavaLib {
        public ConsoleLuajavaLib() {}
        protected Class classForName(String name) throws ClassNotFoundException {
            // Use plain class loader in applets.
            return Class.forName(name);
        }
    }



}
