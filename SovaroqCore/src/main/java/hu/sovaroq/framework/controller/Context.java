package hu.sovaroq.framework.controller;

import hu.sovaroq.framework.service.manager.ServiceManager;
import lombok.Getter;

@Getter
public class Context {
    private final ServiceManager manager;
    private final boolean debug;

    public Context(ServiceManager manager, boolean debug) {
        this.manager = manager;
        this.debug = debug;
    }
}
