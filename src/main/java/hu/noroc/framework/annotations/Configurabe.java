package hu.noroc.framework.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Classes marked with this annotation will automatically registered in the
 * ServiceManager and will receive configurations.
 *
 * Created by Oryk on 2017. 01. 23..
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Configurabe {
    Class<?> configurationClass();
    boolean needsRestart() default true;
}
