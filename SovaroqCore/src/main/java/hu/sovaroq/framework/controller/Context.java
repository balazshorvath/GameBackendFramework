package hu.sovaroq.framework.controller;

import hu.sovaroq.framework.service.manager.ServiceManager;

public class Context {
	private final ServiceManager manager;
	
	public Context(ServiceManager manager){
		this.manager = manager;
	}

	public ServiceManager getManager() {
		return manager;
	}
	
}
