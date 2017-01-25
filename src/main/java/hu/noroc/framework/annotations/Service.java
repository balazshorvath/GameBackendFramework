package hu.noroc.framework.annotations;

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
    String[] methodNames() default {"onEvent"};
    Priority priority() default Priority.LOW;
    String defaultConfig();


    enum Priority {
        HIGH,
        MEDIUM,
        LOW
    }
}
