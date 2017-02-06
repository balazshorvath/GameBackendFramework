package hu.sovaroq.framework.configuration.annotation;

import hu.sovaroq.framework.configuration.FileParser;

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
	 * 		<code>
	 * 			@ConfigFileParser({".json", ".JSON"})
	 * 			public class JSONParser extends FileParser{
	 * 				// Code
	 * 			}
	 * 
	 * 		</code>
	 * @return list of files, that can be parsed using this annotated parser.
	 */
	String[] value();
}
