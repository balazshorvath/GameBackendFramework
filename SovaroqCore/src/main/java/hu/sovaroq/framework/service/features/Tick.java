package hu.sovaroq.framework.service.features;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(METHOD)
public @interface Tick {
	/**
	 * 
	 * @return time between calls in milliseconds
	 */
	long value();
}
