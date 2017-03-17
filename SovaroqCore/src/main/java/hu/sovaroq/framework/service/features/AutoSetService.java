package hu.sovaroq.framework.service.features;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Oryk on 2017. 03. 07..
 */

@Retention(RUNTIME)
@Target(FIELD)
public @interface AutoSetService {
}
