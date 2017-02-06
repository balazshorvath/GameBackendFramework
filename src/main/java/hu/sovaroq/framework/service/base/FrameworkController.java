package hu.sovaroq.framework.service.base;

import hu.sovaroq.framework.annotations.Context;
import hu.sovaroq.framework.events.FrameworkEvent;
import hu.sovaroq.framework.service.IController;

/**
 * Created by Oryk on 2017. 01. 27..
 */
@Context()
public class FrameworkController implements IController<FrameworkEvent, Object> {
    @Override
    public void start(Object o) {

    }

    @Override
    public void stop() {

    }

    @Override
    public String getControllerId() {
        return null;
    }

    @Override
    public void onEvent(FrameworkEvent event) {

    }
}
