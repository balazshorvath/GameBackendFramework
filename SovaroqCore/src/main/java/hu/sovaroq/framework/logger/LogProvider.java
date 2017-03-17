package hu.sovaroq.framework.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogProvider {

	public static Logger createLogger(Class<?> clazz){
		return LogManager.getLogger(clazz);
	}
	
}
