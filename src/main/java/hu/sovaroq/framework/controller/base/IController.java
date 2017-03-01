package hu.sovaroq.framework.controller.base;

import java.util.List;

import hu.sovaroq.framework.core.command.FrameworkCommand;
import hu.sovaroq.framework.service.base.AbstractService;
import hu.sovaroq.framework.service.base.IService;

/**
 * A Controller is a container, a collection of services separated from the framework.
 * A Controller creates it's services it needs to work on startup.
 *
 * Most likely it will use the framework's ServiceManager, however it's possible one
 * might need an own manager.
 * In this case the Controller implementation has to take care of the manager's
 * proper stopping.
 *
 * When stop() is called on a controller, the implementation has to take care
 * of the stopping of the contained services as well.
 *
 * Created by Oryk on 2017. 01. 24..
 */
public interface IController<Ctxt extends Context>{
    void start(Ctxt context);
    void stop();

    Object execute(FrameworkCommand command);

    List<AbstractService> getServices();
    String getStatusDescription();
    Double getWorkload();
}
