package hu.sovaroq.framework.console;

import hu.sovaroq.framework.core.Framework;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LoadState;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.compiler.LuaC;
import org.luaj.vm2.lib.*;
import org.luaj.vm2.lib.jse.*;

import java.io.*;

/**
 * Created by balazs_horvath on 2/23/2017.
 */
public class FrameworkConsole {
    private final Framework framework;
    private final InputStream inputStream;
    private final OutputStream outputStream;
    private boolean running = false;


    public FrameworkConsole(Framework framework, InputStream inputStream, OutputStream outputStream) {
        this.framework = framework;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public void open() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
        String command;
        running = true;

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
        globals.load(new framework.ConsoleLuajavaLib());

        globals.set("framework", CoerceJavaToLua.coerce(framework));

        LoadState.install(globals);
        LuaC.install(globals);

        while (running){
            try {
                if((command = reader.readLine()) == null){
                    Thread.sleep(50);
                    continue;
                }
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
                running = false;
                continue;
            }
            try {
                LuaValue chunk = globals.load(new StringReader(command), "main.lua");

                LuaValue res = chunk.call();
                writer.write(res.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



}
