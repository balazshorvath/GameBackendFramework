package hu.sovaroq.core;

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
import hu.sovaroq.game.data.CommanderBaseRepository;
import hu.sovaroq.game.data.UnitBase;
import hu.sovaroq.game.data.combat.ModifierType;
import org.mindrot.jbcrypt.BCrypt;

import java.util.HashSet;
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
        doUsers();
        doCommanders();
    }

    private void doCommanders(){
        CommanderBaseRepository commanderBaseRepository = (CommanderBaseRepository) database.getRepository(CommanderBase.class);

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
            commanderBaseRepository.save(commander);
        } catch (FrameworkException e) {
            e.printStackTrace();
        }

        //TODO
        buildings = new HashSet<>(2);

        units = new HashSet<>(2);
        units.add(new UnitBase("Barbarian Unit 01", "Barbarian Unit 01 Desc",null, ModifierType.createEmptyStats(),"game/soilder01.lua"));
        units.add(new UnitBase("Barbarian Unit 02", "Barbarian Unit 02 Desc",null, ModifierType.createEmptyStats(),"game/soilder01.lua"));
        buildings.add(new BuildingBase(units, "Barbarian Building One", "Barbarian Desc 01.", 500, 20));

        units = new HashSet<>(2);
        units.add(new UnitBase("Barbarian Unit 03", "Barbarian Unit 03 Desc",null, ModifierType.createEmptyStats(),"game/soilder01.lua"));
        units.add(new UnitBase("Barbarian Unit 04", "Barbarian Unit 04 Desc",null, ModifierType.createEmptyStats(),"game/soilder01.lua"));
        buildings.add(new BuildingBase(units, "Barbarian Building Two", "Barbarian Desc 02.", 1000, 20));

        commander = new CommanderBase("Soilder Nation", "Soilders, blah-blah-blah, modern guns an shit.", buildings);

        try {
            commanderBaseRepository.save(commander);
        } catch (FrameworkException e) {
            e.printStackTrace();
        }
    }


    private void doUsers(){
        UserRepository userRepository = (UserRepository) database.getRepository(User.class);

        String hashedPW = BCrypt.hashpw("pass01", BCrypt.gensalt());

        try {
            UserAccount account = new UserAccount();
            account.setExperiencePoints(1000);
            User user = new User("user01", IUser.UserRole.PLAYER, "user01@gmail.com", hashedPW, false, account);

            userRepository.save(user);

            account = new UserAccount();
            account.setExperiencePoints(1000);
            user = new User("user02", IUser.UserRole.PLAYER, "user02@gmail.com", hashedPW, false, account);

            userRepository.save(user);

            account = new UserAccount();
            account.setExperiencePoints(1000);
            user = new User("user03", IUser.UserRole.PLAYER, "user03@gmail.com", hashedPW, false, account);

            userRepository.save(user);
            account = new UserAccount();
            account.setExperiencePoints(1000);
            user = new User("user04", IUser.UserRole.PLAYER, "user04@gmail.com", hashedPW, false, account);

            userRepository.save(user);

            account = new UserAccount();
            account.setExperiencePoints(1000);
            user = new User("user05", IUser.UserRole.PLAYER, "user05@gmail.com", hashedPW, false, account);

            userRepository.save(user);
        } catch (FrameworkException e) {
            e.printStackTrace();
        }
    }
}
