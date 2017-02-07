package hu.sovaroq.framework.service;

import java.util.concurrent.TimeUnit;

import hu.sovaroq.framework.bus.IEventBus;
import hu.sovaroq.framework.bus.SimpleEventBus;
import hu.sovaroq.framework.events.FrameworkEvent;

public abstract class AbstractController<E extends FrameworkEvent, Config> implements IController<E, Config> {
	private final IEventBus bus = new SimpleEventBus(10, 30, 30, TimeUnit.SECONDS);
	
}
