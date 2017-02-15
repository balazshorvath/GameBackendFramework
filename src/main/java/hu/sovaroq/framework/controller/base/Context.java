package hu.sovaroq.framework.controller.base;

import hu.sovaroq.framework.core.bus.IEventBus;

public class Context {
	private final ServiceManager manager;
	
	public Context(ServiceManager manager){
		this.manager = manager;
	}

	public IEventBus getManager() {
		return manager;
	}
	
}
