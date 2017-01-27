package hu.noroc.framework.annotations;

import hu.noroc.framework.bus.ListenerPriority;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Oryk on 2017. 01. 27..
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EventListener {
    ListenerPriority priority() default ListenerPriority.LOW;
}
