package hu.sovaroq.framework.service.configuration.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * If the key in the config differs from the the one in the class, use this to pair it.
 *
 * Created by Oryk on 2017. 01. 28..
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ConfigValue {
    String key();
}
