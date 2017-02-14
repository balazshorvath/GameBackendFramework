package hu.sovaroq.framework.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Oryk on 2017. 01. 25..
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Service {
    Class<?> configurationClass();
    String configurationFile();
    boolean configNeedsRestart() default false;

}
