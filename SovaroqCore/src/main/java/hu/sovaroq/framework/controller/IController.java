package hu.sovaroq.framework.controller;

import hu.sovaroq.framework.eventing.bus.IEventBus;
import hu.sovaroq.framework.service.AbstractService;

import java.util.List;

/**
 * A Controller is a container, a collection of services separated from the framework.
 * A Controller creates it's services it needs to work on startup.
 * <p>
 * Most likely it will use the framework's ServiceManager, however it's possible one
 * might need an own manager.
 * In this case the Controller implementation has to take care of the manager's
 * proper stopping.
 * <p>
 * When stop() is called on a controller, the implementation has to take care
 * of the stopping of the contained services as well.
 * <p>
 * Created by Oryk on 2017. 01. 24..
 */
public interface IController<Ctxt extends Context> {
    void start(Ctxt context);

    void stop();

    IEventBus getBus();

    List<AbstractService> getServices();

    String getStatusDescription();

    Double getWorkload();
}
