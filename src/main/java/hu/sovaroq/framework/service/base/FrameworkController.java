package hu.sovaroq.framework.service.base;

import hu.sovaroq.framework.annotations.Context;
import hu.sovaroq.framework.events.IFrameworkEvent;
import hu.sovaroq.framework.service.IController;

/**
 * Created by Oryk on 2017. 01. 27..
 */
@Context()
public class FrameworkController implements IController<IFrameworkEvent, Object> {
    @Override
    public void start(Object o) {

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
