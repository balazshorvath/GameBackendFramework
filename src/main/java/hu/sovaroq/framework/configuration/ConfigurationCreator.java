package hu.sovaroq.framework.configuration;

import hu.sovaroq.framework.configuration.annotation.Config;
import hu.sovaroq.framework.configuration.annotation.ConfigValue;
import hu.sovaroq.framework.logger.LogProvider;

import org.apache.logging.log4j.Logger;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.stream.Collectors;

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
            log.error("Could not create config instance of class '" + configType.getName() + "', it's not a '@Config'.");
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
        C result;
        try {
            result = configType.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("Failed to create config class instance of '" + configType.getName() + "'. Exception: " + e.getMessage());
            return null;
        }

        ConfigValue configValue;
        for (Field field : configType.getFields()) {
            if((configValue = field.getAnnotation(ConfigValue.class)) != null){
                String key = configValue.key();
                Config config;
                if(key.isEmpty()){
                    key = field.getName();
                }
                if(values.containsKey(key)){
                    Object value = values.get(key);
                    Object parsedValue = null;
                    try {
                        Method m;
                        if(value instanceof String){
                            parsedValue = parseKnownTypes(field.getType(), (String) value);
                        }else if (value.getClass().equals(field.getType())){
                            parsedValue = value;
                        }
                        m = setter(field);
                        m.invoke(result, parsedValue);

                    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                        log.warn("No setter for field '" + field.getName() + "' in class '" + configType.getName() + "'.");
                    }

                } else if((config = field.getType().getAnnotation(Config.class)) != null){
                    try {
                        Method getter = getter(field);
                        Object fieldValue = getter.invoke(result);
                        if(fieldValue == null){
                            fieldValue = field.getType().newInstance();
                        }
                        final String finalKey = key;
                        Map<String, Object> sublist = values.entrySet().stream().filter(entry ->
                                        entry.getKey().startsWith(finalKey + ".")
                        ).collect(Collectors.toMap(entry ->
                                                entry.getKey().substring(entry.getKey().indexOf(".")),
                                        Map.Entry::getValue)
                        );
                        fieldValue = createObject(field.getType(), sublist);

                        Method setter = setter(field);
                        setter.invoke(result, fieldValue);
                    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                        log.warn("No getter for field '" + field.getName() + "' in class '" + configType.getName() + "'.");
                    } catch (InstantiationException e) {
                        log.warn("Could not create instance of field '" + field.getName() + "'" +
                                " (" + field.getType().getName() + ") in class '" + configType.getName() + "'.");
                    }
                } else {
                    log.warn("Unable to set field '" + field.getName() + "' in class '" + field.getType() + "'.");
                }

            }
        }
        return result;
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

    private Method getter(Field field) throws NoSuchMethodException {
        String base;
        //
        // Lombok generates Boolean as getBoolean
        //
        if (field.getType().equals(boolean.class)){
            base = "is";
        }else {
            base = "get";
        }
        return field.getDeclaringClass().getMethod(base + getFieldNameUpper(field), field.getType());
    }
    private Method setter(Field field) throws NoSuchMethodException {
        return field.getDeclaringClass().getMethod("set" + getFieldNameUpper(field), field.getType());
    }
    private String getFieldNameUpper(Field field){
        return field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
    }
}
