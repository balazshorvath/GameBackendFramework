package hu.sovaroq.framework.configuration;

import hu.sovaroq.framework.configuration.annotation.Config;
import hu.sovaroq.framework.configuration.annotation.ConfigValue;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * Configuration property keys can be separated with dots.
 * For example:
 *     database.name
 *     database.host
 * If the property "database" is present (field marked as @ConfigValue),
 * and the "database" property is also a type with @Config,
 * the database.name property key means config.getDatabase().setName(asdasd);
 *
 *
 * Created by Oryk on 2017. 01. 28..
 */
public class ConfigurationCreator {
    public <C> C createConfig(Class<C> configType, String configFile) {
        Config config = configType.getAnnotation(Config.class);
        if(config == null){
            //log
            return null;
        }
        Map<Object, Object> values = null;
        try {
            values = config.fileParser().newInstance().getConfig(configFile);
        } catch (InstantiationException | IllegalAccessException | IOException e) {
            //log
            e.printStackTrace();
            return null;
        }

        ConfigValue value;
        for (Field field : configType.getFields()) {
            if((value = field.getAnnotation(ConfigValue.class)) != null){
                field.getType();
            }
        }

        return null;
    }
}
