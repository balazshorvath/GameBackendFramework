package hu.sovaroq.framework.service.configuration;

import hu.sovaroq.framework.logger.LogProvider;
import hu.sovaroq.framework.service.configuration.annotation.ConfigFileParser;
import hu.sovaroq.framework.service.configuration.annotation.ConfigValue;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Configuration property keys can be separated with dots.
 * For example:
 * database.name
 * database.host
 * If the property "database" is present (field marked as @ConfigValue),
 * and the "database" property is also a type with @Config,
 * the database.name property key means context.getDatabase().setName(asdasd);
 * <p>
 * <p>
 * Created by Oryk on 2017. 01. 28..
 */
@SuppressWarnings("unchecked")
public class ConfigurationCreator {
    private static final Logger log = LogProvider.createLogger(ConfigurationCreator.class);
    private static final Map<String, List<Class<? extends FileParser>>> fileParsers;

    /**
     * Static initializer for context loader.
     * find file parsers based on annotation.
     */
    static {
        fileParsers = new HashMap<>();
        Reflections reflections = new Reflections();
        Set<Class<?>> parsers = reflections.getTypesAnnotatedWith(ConfigFileParser.class);
        for (Class<?> parser : parsers) {
            ConfigFileParser annot = parser.getAnnotation(ConfigFileParser.class);
            if (!FileParser.class.isAssignableFrom(parser)) {
                log.warn(parser.getName() + " is annotated annotated with @ConfigFileParser, but does not implement FileParser.");
                continue;
            }
            for (String type : annot.value()) {
                if (!fileParsers.containsKey(type)) {
                    fileParsers.put(type, new ArrayList<>());
                }
                fileParsers.get(type).add((Class<? extends FileParser>) parser);
                log.debug("Added file association '" + type + "' with parser " + parser.getName() + ".");
            }
        }
    }


    public <C> C createConfig(Class<C> configType, String configFile) {
        if (configFile == null || configFile.isEmpty() || Object.class.equals(configType)) {
            log.info("No configuration file specified.");
            return null;
        }

        Map<String, Object> values = null;
        String type = configFile.substring(configFile.lastIndexOf('.'));
        if (!fileParsers.containsKey(type)) {
            log.error("No file association.");
            return null;
        }
        for (Class<? extends FileParser> parser : fileParsers.get(type)) {
            try {
                values = parser.newInstance().getConfig(configFile);
                break;
            } catch (InstantiationException | IllegalAccessException | IOException e) {
                log.error(e);
            }
        }

        if (values == null) {
            log.error("Could not parse context file. Config type: " + configType.getName());
        }

        return createObject(configType, values);
    }

    private <C> C createObject(Class<C> configType, Map<String, Object> values) {
        C result;
        try {
            result = configType.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("Failed to create context class instance of '" + configType.getName() + "'. Exception: " + e);
            e.printStackTrace();
            return null;
        }

        ConfigValue configValue;
        for (Field field : configType.getDeclaredFields()) {
            if ((configValue = field.getAnnotation(ConfigValue.class)) != null) {
                String key = configValue.key();
                if (key.isEmpty()) {
                    key = field.getName();
                }
                if (values.containsKey(key)) {
                    Object value = values.get(key);
                    Object parsedValue = null;
                    try {
                        Method m;
                        if (value instanceof String) {
                            parsedValue = parseKnownTypes(field.getType(), (String) value);
                        } else if (value.getClass().equals(field.getType())) {
                            parsedValue = value;
                        }
                        m = setter(field);
                        m.invoke(result, parsedValue);

                    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                        log.warn("No setter for field '" + field.getName() + "' in class '" + configType.getName() + "'.");
                    }

                } else {
                    try {
                        Method getter = getter(field);
                        Object fieldValue = getter.invoke(result);
                        if (fieldValue == null) {
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
                }

            }
        }
        return result;
    }

    private Object parseKnownTypes(Class<?> type, String value) throws NumberFormatException {
        Object o = null;

        if (type.isPrimitive() || Number.class.isAssignableFrom(type) || type.equals(Boolean.class) || type.equals(Character.class)) {
            if (type.equals(float.class) || type.equals(Float.class)) {
                o = Float.parseFloat(value);
            } else if (type.equals(double.class) || type.equals(Double.class)) {
                o = Double.parseDouble(value);
            } else if (type.equals(byte.class) || type.equals(Byte.class)) {
                o = Byte.parseByte(value);
            } else if (type.equals(char.class) || type.equals(Character.class)) {
                if (value.length() > 1) {
                    log.warn("parseKnownTypes: Field type is char, but the value is longer, than 1. Using the first character.");
                }
                if (value.length() < 1) {
                    o = '\u0000';
                } else {
                    o = value.charAt(0);
                }
            } else if (type.equals(short.class) || type.equals(Short.class)) {
                o = Short.parseShort(value);
            } else if (type.equals(int.class) || type.equals(Integer.class)) {
                o = Integer.parseInt(value);
            } else if (type.equals(long.class) || type.equals(Long.class)) {
                o = Long.parseLong(value);
            } else {
                // Every other possible type are checked.
                o = Boolean.parseBoolean(value);
            }
        } else if (type.equals(String.class) || type.equals(Object.class)) {
            o = value;
        }

        return o;
    }

    private Method getter(Field field) throws NoSuchMethodException {
        String base;
        //
        // Lombok generates Boolean as getBoolean
        //
        if (field.getType().equals(boolean.class)) {
            base = "is";
        } else {
            base = "get";
        }
        return field.getDeclaringClass().getMethod(base + getFieldNameUpper(field), field.getType());
    }

    private Method setter(Field field) throws NoSuchMethodException {
        return field.getDeclaringClass().getMethod("set" + getFieldNameUpper(field), field.getType());
    }

    private String getFieldNameUpper(Field field) {
        return field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
    }
}
