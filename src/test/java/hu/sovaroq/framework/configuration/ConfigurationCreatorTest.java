package hu.sovaroq.framework.configuration;

import hu.sovaroq.framework.configuration.annotation.Config;
import hu.sovaroq.framework.configuration.annotation.ConfigValue;
import hu.sovaroq.game.core.config.DefaultFileParser;
import junit.framework.Assert;
import lombok.Data;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.zip.InflaterInputStream;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Oryk on 2017. 02. 04..
 */
public class ConfigurationCreatorTest {
    private final String testConfig1 = "testconfiguration1.properties";
    private ConfigurationCreator configurationCreator;
    

    @Before
    public void setup(){
        configurationCreator = new ConfigurationCreator();
    }
    
    @Test
    public void testParsing() throws IOException {
        TestConfig config = configurationCreator.createConfig(TestConfig.class, testConfig1));
        Properties properties = new Properties();
        properties.load(Files.newInputStream(Paths.get(testConfig1)));
        
        assertEquals(Byte.valueOf((String) properties.get("aByte")), config.getAByte());
        assertEquals(properties.get("objectByte"), config.getOByte());
        
        assertEquals(properties.get("aChar"), config.getAChar());
        assertEquals(properties.get("character"), config.getCharacter());
        
        assertEquals(properties.get("aShort"), config.getAShort());
        assertEquals(properties.get("oShort"), config.getOShort());
        
        assertEquals(properties.get("anInt"), config.getAnInt());
        assertEquals(properties.get("integer"), config.getInteger());
        
        assertEquals(properties.get("aLong"), config.getALong());
        assertEquals(properties.get("oLong"), config.getOLong());
        
        assertEquals(properties.get("aFloat"), config.getAFloat());
        assertEquals(properties.get("oFloat"), config.getOFloat());
        
        assertEquals(properties.get("aDouble"), config.getADouble());
        assertEquals(properties.get("oDouble"), config.getODouble());
        
        assertEquals(properties.get("aBoolean"), config.isABoolean());
        assertEquals(properties.get("oBoolean"), config.getOBoolean());
        
        assertEquals(properties.get("string"), config.getString());
        
        assertEquals(properties.get("object"), config.getObject());

        assertEquals(properties.get("notConfigured"), config.getNotConfigured());
    }
    


    @Config(fileParser = DefaultFileParser.class)
    @Data
    private class TestConfig {
        @ConfigValue
        private byte aByte;
        @ConfigValue(key = "objectByte")
        private Byte oByte;

        @ConfigValue
        private char aChar;
        @ConfigValue
        private Character character;

        @ConfigValue
        private short aShort;
        @ConfigValue
        private Short oShort;

        @ConfigValue
        private int anInt;
        @ConfigValue
        private Integer integer;

        @ConfigValue
        private long aLong;
        @ConfigValue
        private Long oLong;

        @ConfigValue
        private double aDouble;
        @ConfigValue
        private Double oDouble;

        @ConfigValue
        private float aFloat;
        @ConfigValue
        private Float oFloat;

        @ConfigValue
        private boolean aBoolean;
        @ConfigValue
        private Boolean oBoolean;

        @ConfigValue
        private String string;

        @ConfigValue
        private Object object;

        private Integer notConfigured;

        private TestConfig() {
        }
    }
}
