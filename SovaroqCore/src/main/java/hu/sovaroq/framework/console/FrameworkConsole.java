package hu.sovaroq.framework.console;

import hu.sovaroq.framework.Framework;
import hu.sovaroq.framework.scripting.LuaGlobalsProvider;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

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

    public void open() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
        String command;
        running = true;
        LuaGlobalsProvider lua = (LuaGlobalsProvider) framework.getManager().getService(LuaGlobalsProvider.class);
        if (lua == null) {
            writer.write("No lua provider found.");
            return;
        }
        Globals globals = lua.getGlobals();
        globals.set("framework", CoerceJavaToLua.coerce(framework));

        while (running) {
            try {
                if ((command = reader.readLine()) == null) {
                    Thread.sleep(50);
                    continue;
                }
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
                running = false;
                continue;
            }
            if ("exit".equals(command)) {
                running = false;
                continue;
            }
            try {
                LuaValue chunk = globals.load(new StringReader(command), "luaConsole");

                LuaValue res = chunk.call();
                writer.write(res.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
