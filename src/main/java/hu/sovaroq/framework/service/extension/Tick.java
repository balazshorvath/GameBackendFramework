package hu.sovaroq.framework.service.extension;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(METHOD)
public @interface Tick {
	/**
	 * 
	 * @return time between calls in milliseconds
	 */
	long value();
}
