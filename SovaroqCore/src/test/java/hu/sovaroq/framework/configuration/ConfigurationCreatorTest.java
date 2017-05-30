package hu.sovaroq.framework.configuration;

import hu.sovaroq.framework.service.configuration.ConfigurationCreator;
import hu.sovaroq.framework.service.configuration.annotation.ConfigValue;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by Oryk on 2017. 02. 04..
 */
public class ConfigurationCreatorTest {
    private final String testConfig1 = "src/test/resources/configuration/testconfiguration1.properties";
    private ConfigurationCreator configurationCreator;


    @Before
    public void setup() {
        configurationCreator = new ConfigurationCreator();
    }

    @Test
    public void testParsing() throws IOException {
        TestConfig config = configurationCreator.createConfig(TestConfig.class, testConfig1);
        Properties properties = new Properties();
        properties.load(Files.newInputStream(Paths.get(testConfig1)));

        assertEquals(Byte.valueOf((String) properties.get("aByte")), new Byte(config.getAByte()));
        assertEquals(Byte.valueOf((String) properties.get("objectByte")), config.getOByte());

        assertEquals(((String) properties.get("aChar")).charAt(0), config.getAChar());
        assertEquals(new Character(((String) properties.get("character")).charAt(0)), config.getCharacter());

        assertEquals(Short.valueOf((String) properties.get("aShort")), new Short(config.getAShort()));
        assertEquals(Short.valueOf((String) properties.get("oShort")), config.getOShort());

        assertEquals(Integer.valueOf((String) properties.get("anInt")), new Integer(config.getAnInt()));
        assertEquals(Integer.valueOf((String) properties.get("integer")), config.getInteger());

        assertEquals(Long.valueOf((String) properties.get("aLong")), new Long(config.getALong()));
        assertEquals(Long.valueOf((String) properties.get("oLong")), config.getOLong());

        assertEquals(Float.valueOf((String) properties.get("aFloat")), new Float(config.getAFloat()));
        assertEquals(Float.valueOf((String) properties.get("oFloat")), config.getOFloat());

        assertEquals(Double.valueOf((String) properties.get("aDouble")), new Double(config.getADouble()));
        assertEquals(Double.valueOf((String) properties.get("oDouble")), config.getODouble());

        assertEquals(Boolean.valueOf((String) properties.get("aBoolean")), new Boolean(config.isABoolean()));
        assertEquals(Boolean.valueOf((String) properties.get("oBoolean")), config.getOBoolean());

        assertEquals(properties.get("string"), config.getString());

        assertEquals(properties.get("object"), config.getObject());

        assertNotNull(config.subConfig);
        assertEquals(Integer.valueOf((String) properties.get("subConfig.key")), config.subConfig.key);
        assertEquals(properties.get("subConfig.value"), config.subConfig.value);

        assertNull(config.getNotConfigured());
    }


    @Data
    @NoArgsConstructor
    public static class TestConfig {
        private byte aByte;
        @ConfigValue(key = "objectByte")
        private Byte oByte;

        private char aChar;
        private Character character;

        private short aShort;
        private Short oShort;

        private int anInt;
        private Integer integer;

        private long aLong;
        private Long oLong;

        private double aDouble;
        private Double oDouble;

        private float aFloat;
        private Float oFloat;

        private boolean aBoolean;
        private Boolean oBoolean;

        private String string;

        private Object object;

        @ConfigValue(key = "subConfig")
        private TestSubConfig subConfig;

        private Integer notConfigured;
    }

    @Data
    @NoArgsConstructor
    public static class TestSubConfig {
        private Integer key;
        private String value;
    }
}
