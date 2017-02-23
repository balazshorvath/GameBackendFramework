package hu.sovaroq.framework.controller.base;

import org.apache.logging.log4j.Logger;

import hu.sovaroq.framework.core.ServiceManager;
import hu.sovaroq.framework.core.logger.LogProvider;

public abstract class AbstractController<Ctxt extends Context> implements IController<Ctxt> {

	protected Logger log = LogProvider.createLogger(this.getClass());
	protected Ctxt context;
	protected ServiceManager manager;
	
	@Override
	public void start(Ctxt context) {
		this.context = context;
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
