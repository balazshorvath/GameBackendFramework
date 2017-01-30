package hu.sovaroq.framework.configuration;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Oryk on 2017. 01. 28..
 */
public abstract class FileParser {
    public FileParser() {
    }
    public abstract Map<Object, Object> getConfig(String file) throws IOException;
    public abstract void saveConfig(String file, Map<Object, Object> config) throws IOException;
}
