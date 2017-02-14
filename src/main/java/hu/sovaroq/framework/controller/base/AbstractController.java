package hu.sovaroq.framework.controller.base;

import org.apache.logging.log4j.Logger;

import hu.sovaroq.framework.core.logger.LogProvider;
import hu.sovaroq.framework.events.FrameworkEvent;

public abstract class AbstractController<E extends FrameworkEvent, Config> implements IController<E, Config> {
	protected Logger log = LogProvider.createLogger(this.getClass());
	protected Config config;
	
	@Override
	public void start(Config config) {
		this.config = config;
	}

	@Override
	public void stop() {
	}

	@Override
	public String getStatusDescription() {
		return "Status description is unimplemented.";
	}

	@Override
	public Double getWorkload() {
		return 0.0;
	}
	
}
