package hu.sovaroq.framework.controller.base;

import hu.sovaroq.framework.core.bus.IEventBus;

public class Context {
	private final IEventBus bus;
	
	public Context(IEventBus bus){
		this.bus = bus;
	}

	public IEventBus getBus() {
		return bus;
	}
	
}
