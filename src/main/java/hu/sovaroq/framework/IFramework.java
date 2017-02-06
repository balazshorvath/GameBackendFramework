package hu.sovaroq.framework;


import java.util.List;

import hu.sovaroq.framework.service.IController;

/**
 * Responsible for:
 *  a) On start, register IServiceManager.
 *  b) Register MasterServices through <code>registerMasterService();<code/>
 *  c) Manage the main event bus.
 *
 *
 * Created by Oryk on 2017. 01. 23..
 */
public interface IFramework {
    void start();
    void stop();

    IFramework getInstance();

    <T extends IController> void registerController(Class<T> c, T controller);
    IController getControllerById(String id);
    List<IController> getControllers();

    String getStatusDescription();
    Double getWorkload();
}
