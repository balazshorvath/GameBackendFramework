package hu.noroc.framework.service.base;

import hu.noroc.framework.annotations.Context;
import hu.noroc.framework.events.IFrameworkEvent;
import hu.noroc.framework.service.IContext;

/**
 * Created by Oryk on 2017. 01. 27..
 */
@Context()
public class FrameworkContext implements IContext<IFrameworkEvent> {
    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public String getContextId() {
        return null;
    }

    @Override
    public void onEvent(IFrameworkEvent event) {

    }
}
