package hu.noroc.framework.service;

import hu.noroc.framework.events.IFrameworkEvent;
import hu.noroc.framework.service.base.config.model.IServiceConfig;


/**
 * A Context is a container, a collection of services separated from the framework.
 *
 * A Context is listening for one base type of event, which will be posted on the
 * inner event bus.
 *
 * A Context will create/update/stop it's own services based on a configuration implementation.
 *
 * Created by Oryk on 2017. 01. 24..
 */
public interface IContext<E extends IFrameworkEvent, C extends IServiceConfig> extends IConfigurableService<C>{
    void start();
    void stop();

    String getConttextId();

    void onEvent(E event);
}
