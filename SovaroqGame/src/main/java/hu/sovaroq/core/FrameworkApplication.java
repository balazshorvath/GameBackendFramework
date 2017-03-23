package hu.sovaroq.core;

import hu.sovaroq.framework.Framework;
import hu.sovaroq.framework.console.FrameworkConsole;
import hu.sovaroq.framework.controller.AbstractController;
import hu.sovaroq.game.GameController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The entry-point.
 * <p>
 * Created by balazs_horvath on 2/23/2017.
 */
public class FrameworkApplication {
    public static void main(String[] args) {
        String frameworkConfig = "src/main/resources/Framework.features";
        boolean debug = false;
        List<Class<? extends AbstractController>> features = new ArrayList<>();
        if (args.length < 1) {
            printUsage();
            System.out.println("Using defaults '" + frameworkConfig + "' and '" + debug + "'.");
        } else {
            frameworkConfig = args[0];
            if (args.length == 2) {
                debug = Boolean.parseBoolean(args[1]);
            }
        }
        try (FileReader reader = new FileReader(frameworkConfig)) {
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Class c = Class.forName(line);
                if (c != null && AbstractController.class.isAssignableFrom(c))
                    features.add(c);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        Framework framework = new Framework();

        framework.setDebug(true);
        framework.start(features);
        framework.registerController(GameController.class);
        System.out.println("Framework started.\nLua console:");
        FrameworkConsole console = new FrameworkConsole(framework, System.in, System.out);

        try {
            console.open();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            framework.stop();
        }
    }

    private static void printUsage() {
        System.out.println("Usage:\n\t{path/to/framework.features} {debug [true/false]}");
    }
}
