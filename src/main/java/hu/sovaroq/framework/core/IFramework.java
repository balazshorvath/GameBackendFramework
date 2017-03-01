package hu.sovaroq.framework.core;


import java.util.List;

import hu.sovaroq.framework.controller.base.AbstractController;
import hu.sovaroq.framework.controller.base.IController;
import hu.sovaroq.framework.core.command.FrameworkCommand;

/**
 * Created by Oryk on 2017. 01. 23..
 */
public interface IFramework {
    void start(List<Class<? extends AbstractController>> features);
    void stop();

    Object execute(FrameworkCommand command);

    List<AbstractController> getControllers();

    String getStatusDescription();
    Double getWorkload();
}
