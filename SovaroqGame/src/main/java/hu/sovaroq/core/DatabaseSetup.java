package hu.sovaroq.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.sovaroq.core.database.repository.HibernateRepository;
import hu.sovaroq.core.database.service.HibernateDatabaseService;
import hu.sovaroq.core.user.authentication.User;
import hu.sovaroq.framework.exception.FrameworkException;
import hu.sovaroq.framework.service.manager.ServiceManager;
import hu.sovaroq.game.data.CommanderBase;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by balazs_horvath on 4/3/2017.
 */

public class DatabaseSetup {

    public static void main(String[] args) throws IOException, FrameworkException {
        if(args.length != 2){
            printUsage();
                return;
        }
        ServiceManager manager = new ServiceManager(1, 1, 1);
        HibernateDatabaseService service = manager.manage(HibernateDatabaseService.class);
        manager.start();

        HibernateRepository<User> userRepo = service.getRepository(User.class);
        HibernateRepository<CommanderBase> commanderRepo = service.getRepository(CommanderBase.class);

        User[] users = new ObjectMapper().readValue(Files.readAllBytes(Paths.get(args[0])), User[].class);

        for (User u : users) {
            userRepo.save(u);
        }

        CommanderBase[] commanders = new ObjectMapper().readValue(Files.readAllBytes(Paths.get(args[1])), CommanderBase[].class);

        for (CommanderBase c : commanders) {
            commanderRepo.save(c);
        }
    }
    private static void printUsage() {
        System.out.println("Usage:\n\t{path/to/Users.json} {path/to/Commanders.json}");
    }
}
