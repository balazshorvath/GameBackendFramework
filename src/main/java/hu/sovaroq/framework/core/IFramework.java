package hu.sovaroq.framework.core;


import java.util.List;

import hu.sovaroq.framework.controller.base.AbstractController;
import hu.sovaroq.framework.controller.base.IController;

/**
 * Created by Oryk on 2017. 01. 23..
 */
public interface IFramework {
    void start(List<Class<?>> features);
    void stop();

    List<AbstractController> getControllers();

    String getStatusDescription();
    Double getWorkload();
}
