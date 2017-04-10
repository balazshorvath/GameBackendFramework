package hu.sovaroq.core;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.sovaroq.core.database.service.HibernateDatabaseService;
import hu.sovaroq.core.user.account.UserAccount;
import hu.sovaroq.core.user.authentication.IUser;
import hu.sovaroq.core.user.authentication.User;
import hu.sovaroq.core.user.authentication.UserRepository;
import hu.sovaroq.framework.exception.FrameworkException;
import hu.sovaroq.framework.service.AbstractService;
import hu.sovaroq.framework.service.Service;
import hu.sovaroq.framework.service.features.AutoSetService;
import hu.sovaroq.game.data.BuildingBase;
import hu.sovaroq.game.data.CommanderBase;
import hu.sovaroq.game.data.UnitBase;
import hu.sovaroq.game.data.combat.ModifierType;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by balazs_horvath on 4/3/2017.
 */
@Service
public class DatabaseSetup extends AbstractService<Object> {
    @AutoSetService
    private HibernateDatabaseService database;

    @Override
    public void start(Object o) {
        super.start(o);
    }

    public static void main(String[] args) throws JsonProcessingException {
        doCommanders("d:\\project_files\\GameBackendFramework\\SovaroqGame\\src\\main\\resources\\database\\test_data\\Commander01.json");
        doUsers("d:\\project_files\\GameBackendFramework\\SovaroqGame\\src\\main\\resources\\database\\test_data\\Users.json");
    }
    public static void doCommanders(String file) throws JsonProcessingException {
        Set<BuildingBase> buildings;
        Set<UnitBase> units;
        CommanderBase commander;

        buildings = new HashSet<>(2);

        units = new HashSet<>(2);
        units.add(new UnitBase("Soilder Unit 01", "Soilder Unit 01 Desc",null, ModifierType.createEmptyStats(),"game/soilder01.lua"));
        units.add(new UnitBase("Soilder Unit 02", "Soilder Unit 02 Desc",null, ModifierType.createEmptyStats(),"game/soilder01.lua"));
        buildings.add(new BuildingBase(units, "Soilder Building One", "Soilder Desc 01.", 500, 20));

        units = new HashSet<>(2);
        units.add(new UnitBase("Soilder Unit 03", "Soilder Unit 03 Desc",null, ModifierType.createEmptyStats(),"game/soilder01.lua"));
        units.add(new UnitBase("Soilder Unit 04", "Soilder Unit 04 Desc",null, ModifierType.createEmptyStats(),"game/soilder01.lua"));
        buildings.add(new BuildingBase(units, "Soilder Building Two", "Soilder Desc 02.", 1000, 20));

        commander = new CommanderBase("Soilder Nation", "Soilders, blah-blah-blah, modern guns an shit.", buildings);

        try {
            new ObjectMapper().writeValue(Paths.get(file).toFile(), commander);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void doUsers(String file){
        String hashedPW = BCrypt.hashpw("pass01", BCrypt.gensalt());
        List<User> users = new ArrayList<>();
        try {
            UserAccount account = new UserAccount();
            account.setExperiencePoints(1000);
            User user = new User("user01", IUser.UserRole.PLAYER, "user01@gmail.com", hashedPW, false, account);

            users.add(user);

            account = new UserAccount();
            account.setExperiencePoints(1000);
            user = new User("user02", IUser.UserRole.PLAYER, "user02@gmail.com", hashedPW, false, account);

            users.add(user);

            account = new UserAccount();
            account.setExperiencePoints(1000);
            user = new User("user03", IUser.UserRole.PLAYER, "user03@gmail.com", hashedPW, false, account);

            users.add(user);

            account = new UserAccount();
            account.setExperiencePoints(1000);
            user = new User("user04", IUser.UserRole.PLAYER, "user04@gmail.com", hashedPW, false, account);

            users.add(user);

            account = new UserAccount();
            account.setExperiencePoints(1000);
            user = new User("user05", IUser.UserRole.PLAYER, "user05@gmail.com", hashedPW, false, account);

            users.add(user);

            new ObjectMapper().writeValue(Paths.get(file).toFile(), users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
