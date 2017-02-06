package hu.sovaroq.framework.configuration;

import hu.sovaroq.framework.configuration.annotation.Config;
import hu.sovaroq.framework.configuration.annotation.ConfigValue;
import hu.sovaroq.game.core.config.DefaultFileParser;
import lombok.Data;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.zip.InflaterInputStream;

import static org.junit.Assert.*;

/**
 * Created by Oryk on 2017. 02. 04..
 */
public class ConfigurationCreatorTest {
    private final String testConfig1 = "src/test/resources/configuration/testconfiguration1.properties";
    private ConfigurationCreator configurationCreator;
    

    @Before
    public void setup(){
        configurationCreator = new ConfigurationCreator();
    }
    
    @Test
    public void testParsing() throws IOException {
    	// Git test
        TestConfig config = configurationCreator.createConfig(TestConfig.class, testConfig1);
        Properties properties = new Properties();
        properties.load(Files.newInputStream(Paths.get(testConfig1)));
        
        assertEquals(Byte.valueOf((String) properties.get("aByte")), new Byte(config.getAByte()));
        assertEquals(Byte.valueOf((String) properties.get("objectByte")), config.getOByte());
        
        assertEquals(((String)properties.get("aChar")).charAt(0), config.getAChar());
        assertEquals(new Character(((String)properties.get("character")).charAt(0)), config.getCharacter());
        
        assertEquals(Short.valueOf((String)properties.get("aShort")), new Short(config.getAShort()));
        assertEquals(Short.valueOf((String)properties.get("oShort")), config.getOShort());
        
        assertEquals(Integer.valueOf((String)properties.get("anInt")), new Integer(config.getAnInt()));
        assertEquals(Integer.valueOf((String)properties.get("integer")), config.getInteger());
        
        assertEquals(Long.valueOf((String)properties.get("aLong")), new Long(config.getALong()));
        assertEquals(Long.valueOf((String)properties.get("oLong")), config.getOLong());
        
        assertEquals(Float.valueOf((String)properties.get("aFloat")), new Float(config.getAFloat()));
        assertEquals(Float.valueOf((String)properties.get("oFloat")), config.getOFloat());
        
        assertEquals(Double.valueOf((String)properties.get("aDouble")), new Double(config.getADouble()));
        assertEquals(Double.valueOf((String)properties.get("oDouble")), config.getODouble());
        
        assertEquals(Boolean.valueOf((String)properties.get("aBoolean")), new Boolean(config.isABoolean()));
        assertEquals(Boolean.valueOf((String)properties.get("oBoolean")), config.getOBoolean());
        
        assertEquals(properties.get("string"), config.getString());
        
        assertEquals(properties.get("object"), config.getObject());

        assertNull(config.getNotConfigured());
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

        public TestConfig() {
        }
    }
}
