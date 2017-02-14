package hu.sovaroq.framework.core.configuration;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Oryk on 2017. 01. 28..
 */
public abstract class FileParser {
    public FileParser() {
    }
    public abstract Map<String, Object> getConfig(String file) throws IOException;
    public abstract void saveConfig(String file, Map<String, Object> config) throws IOException;
}
