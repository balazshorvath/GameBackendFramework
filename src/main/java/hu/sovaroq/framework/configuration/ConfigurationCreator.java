package hu.sovaroq.framework.configuration;

import hu.sovaroq.framework.configuration.annotation.Config;
import hu.sovaroq.framework.configuration.annotation.ConfigValue;
import org.apache.logging.log4j.Logger;

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
    private static final Logger log = LogProvider.createLogger(ConfigurationCreator.class);

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
            e.printStackTrace();
            return null;
        }


        return null;
    }

    private <C> C createObject(Class<C> configType, Map<String, Object> values){

        ConfigValue configValue;
        for (Field field : configType.getFields()) {
            if((configValue = field.getAnnotation(ConfigValue.class)) != null){
                Config config;
                if((config = field.getType().getAnnotation(Config.class)) != null){
                    // TODO if key exists exactly as annotated ( there's no something.something in the key set ), act as it's a new config file.

                    // TODO if key exists as the beginning of multiple keys followed by a dot in the hash map ( "keyname.whatever" ),create "submap"
                    // TODO with the new keys ( new key would be "whatever" )
                }

                if(values.containsKey(configValue.key())){
                    Object value = values.get(configValue.key());
                    Object parsedValue = null;
                    if(value instanceof String){
                         parsedValue = parseKnownTypes(field.getType(), (String) value);
                        //TODO try for @Config class, if yes, then
                    }else if (value.getClass().equals(field.getType())){
                        //TODO setter
                    }

                }
            }
        }
        return null;
    }

    private Object parseKnownTypes(Class<?> type, String value) throws NumberFormatException {
        Object o = null;

        if(type.isPrimitive() || type.isAssignableFrom(Number.class) || type.equals(Boolean.class) || type.equals(Character.class)){
            if(type.equals(float.class) || type.equals(Float.class)){
                o = Float.parseFloat(value);
            }else if(type.equals(double.class) || type.equals(Double.class)){
                o = Double.parseDouble(value);
            }else if(type.equals(byte.class) || type.equals(Byte.class)){
                o = Byte.parseByte(value);
            }else if(type.equals(char.class) || type.equals(Character.class)){
                if(value.length() > 1){
                    log.warn("parseKnownTypes: Field type is char, but the value is longer, than 1. Using the first character.");
                }
                if(value.length() < 1){
                    o = '\u0000';
                }else {
                    o = value.charAt(0);
                }
            }else if(type.equals(short.class) || type.equals(Short.class)){
                o = Short.parseShort(value);
            }else if(type.equals(int.class) || type.equals(Integer.class)){
                o = Integer.parseInt(value);
            }else if(type.equals(long.class) || type.equals(Long.class)){
                o = Long.parseLong(value);
            }else {
                // Every other possible type are checked.
                o = Boolean.parseBoolean(value);
            }
        }else if(type.equals(String.class) || type.equals(Object.class)){
            o = value;
        }

        return o;
    }
}
