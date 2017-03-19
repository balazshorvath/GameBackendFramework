package hu.sovaroq.framework.service.configuration.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Oryk on 2017. 01. 28..
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ConfigFileParser {
    /**
     * Example:
     * <code>
     *
     * @return list of files, that can be parsed using this annotated parser.
     * @ConfigFileParser({".json", ".JSON"})
     * public class JSONParser extends FileParser{
     * // Code
     * }
     * <p>
     * </code>
     */
    String[] value();
}
