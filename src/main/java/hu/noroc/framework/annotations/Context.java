package hu.noroc.framework.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Classes annotated with @Context will be processed on startup.
 * Only classes with this annotation can be published in the Framework.
 *
 * Created by Oryk on 2017. 01. 25..
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Context {
    String[] methodNames() default {"onEvent"};
    /**
     * Based on this value, events will be passed first to HIGH priority listeners,
     * then MEDIUM and lastly to LOW.
     *
     * @return priority of the Context
     */
    Priority priority() default Priority.LOW;
    String defaultConfig();

    enum Priority {
        HIGH,
        MEDIUM,
        LOW
    }
}
