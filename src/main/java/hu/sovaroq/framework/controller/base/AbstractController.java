package hu.sovaroq.framework.controller.base;

import hu.sovaroq.framework.core.ServiceManager;
import hu.sovaroq.framework.core.logger.LogProvider;
import hu.sovaroq.framework.service.base.AbstractService;
import org.apache.logging.log4j.Logger;

public abstract class AbstractController<Ctxt extends Context> implements IController<Ctxt> {

	protected Logger log = LogProvider.createLogger(this.getClass());
	protected Ctxt context;
	protected ServiceManager manager;
	
	@Override
	public void start(Ctxt context) {
		this.context = context;
		this.manager = context.getManager();
	}

	@Override
	public void stop() {
		getServices().forEach(AbstractService::stop);
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
