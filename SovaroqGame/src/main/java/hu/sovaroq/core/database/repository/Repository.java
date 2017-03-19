package hu.sovaroq.core.database.repository;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Repositories are loaded if they are marked with this annotation
 * <p>
 * Created by Oryk on 2017. 02. 22..
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Repository {
}
