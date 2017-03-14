package hu.sovaroq.framework.core;


import hu.sovaroq.framework.controller.base.AbstractController;

import java.util.List;

/**
 * Created by Oryk on 2017. 01. 23..
 */
public interface IFramework {
    void start(List<Class<? extends AbstractController>> features);
    void stop();


    List<AbstractController> getControllers();

    String getStatusDescription();
    Double getWorkload();
}
