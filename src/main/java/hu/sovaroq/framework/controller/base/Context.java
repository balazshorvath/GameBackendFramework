package hu.sovaroq.framework.controller.base;

import hu.sovaroq.framework.core.ServiceManager;

public class Context {
	private final ServiceManager manager;
	
	public Context(ServiceManager manager){
		this.manager = manager;
	}

	public ServiceManager getManager() {
		return manager;
	}
	
}
