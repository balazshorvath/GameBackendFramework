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
public @interface Config {
    Class<? extends FileParser> fileParser();

}
