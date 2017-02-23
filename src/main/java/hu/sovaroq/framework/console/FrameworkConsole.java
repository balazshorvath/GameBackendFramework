package hu.sovaroq.framework.console;

import hu.sovaroq.framework.core.Framework;
import hu.sovaroq.framework.service.base.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by balazs_horvath on 2/23/2017.
 */
public class FrameworkConsole {
    private final Framework framework;
    private final InputStream inputStream;
    private boolean running = false;


    public FrameworkConsole(Framework framework, InputStream inputStream) {
        this.framework = framework;
        this.inputStream = inputStream;
    }

    public void open() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String command;
        running = true;

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
            switch (command){
                case "stop":
                    framework.stop();
                    running = false;
                    break;
            }
        }
    }



}
