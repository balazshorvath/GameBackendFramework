package hu.sovaroq.framework.controller.base;

import hu.sovaroq.framework.core.command.FrameworkCommand;
import hu.sovaroq.framework.core.command.ServiceCommand;
import hu.sovaroq.framework.service.base.AbstractService;
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
		this.manager = context.getManager();
	}

	@Override
	public Object execute(FrameworkCommand command) {
		Object result = null;
		if(command instanceof ServiceCommand){
			switch (((ServiceCommand) command).getServiceCommandType()){
				case CREATE:
					result = manager.manage(((ServiceCommand) command).getService());
					break;
				case RESTART:
					manager.restartService(((ServiceCommand) command).getService());
					result = true;
					break;
				case STOP:
					manager.stopService(((ServiceCommand) command).getService());
					result = true;
					break;
			}
		}
		return result;
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
