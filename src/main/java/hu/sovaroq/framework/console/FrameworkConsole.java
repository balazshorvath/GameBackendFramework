package hu.sovaroq.framework.console;

import hu.sovaroq.framework.core.Framework;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

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

        Globals globals = JsePlatform.standardGlobals();
        globals.set("api", new FrameworkAPI(framework));

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
            LuaValue chunk = globals.load(command);

            LuaValue res = chunk.call();

            try {
                writer.write(res.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
