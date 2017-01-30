package hu.sovaroq.framework;


import hu.sovaroq.framework.service.IContext;

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

    <T extends IContext> void registerMasterService(Class<T> c, T service);
    <T extends IContext> T getMasterServiceById(String id);

    String getStatusDescription();
    Double getWorkload();
}
