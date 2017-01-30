package hu.noroc.game.core.config;

import hu.noroc.framework.configuration.FileParser;

import java.io.BufferedWriter;
import java.io.CharArrayWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Oryk on 2017. 01. 28..
 */
public class DefaultFileParser extends FileParser {
    public DefaultFileParser() {
        super();
    }

    @Override
    public Map<Object, Object> getConfig(String file) throws IOException {
        final Path path = Paths.get(file);
        final Map<Object, Object> props = new HashMap<>();
        if(path.toFile().exists()){
            Properties properties = new Properties();

            properties.load(Files.newInputStream(path));
            properties.entrySet().forEach(entry -> props.put(entry.getKey(), entry.getValue()));
        }
        return props;
    }

    @Override
    public void saveConfig(String file, Map<Object, Object> config) throws IOException {
        Properties properties = new Properties();
        properties.putAll(config);
        properties.store(new BufferedWriter(new FileWriter(file)),"NOCOMMENT");
    }
}
