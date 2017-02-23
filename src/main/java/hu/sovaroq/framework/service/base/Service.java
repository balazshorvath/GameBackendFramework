package hu.sovaroq.framework.service.base;

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
    Class<?> configurationClass() default Object.class;
    String configurationFile() default "";
    boolean configNeedsRestart() default false;

}
