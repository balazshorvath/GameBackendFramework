package hu.sovaroq.framework;

import hu.sovaroq.framework.controller.AbstractController;

import java.util.List;

/**
 * Created by Oryk on 2017. 01. 23..
 */
public interface IFramework {
    void start(List<Class<? extends AbstractController>> features);

    void stop();

    List<AbstractController> getControllers();

    void setDebug(boolean debug);

    String getStatusDescription();

    Double getWorkload();
}
