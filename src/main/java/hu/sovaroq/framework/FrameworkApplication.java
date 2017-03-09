package hu.sovaroq.framework;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.StandardSocketOptions;
import java.util.ArrayList;
import java.util.List;

import hu.sovaroq.framework.console.FrameworkConsole;
import hu.sovaroq.framework.controller.base.AbstractController;
import hu.sovaroq.framework.core.Framework;

/**
 * The entry-point.
 *
 * Created by balazs_horvath on 2/23/2017.
 */
public class FrameworkApplication {
    public static void main(String[] args){
        String frameworkConfig = "src/main/resources/Framework.features";
        List<Class<? extends AbstractController>> features = new ArrayList<>();
        if(args.length > 0){
            frameworkConfig = args[0];
        }
        try (FileReader reader = new FileReader(frameworkConfig)){
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            while((line = bufferedReader.readLine()) != null){
                Class c = Class.forName(line);
                if(c != null && AbstractController.class.isAssignableFrom(c))
                    features.add(c);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        Framework framework = new Framework();

        framework.start(features);
        FrameworkConsole console = new FrameworkConsole(framework, System.in, System.out);

        console.open();
    }

}
