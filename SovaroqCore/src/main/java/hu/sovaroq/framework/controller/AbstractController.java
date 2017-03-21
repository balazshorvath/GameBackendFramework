package hu.sovaroq.framework.controller;

import hu.sovaroq.framework.logger.LogProvider;
import hu.sovaroq.framework.service.AbstractService;
import hu.sovaroq.framework.service.manager.ServiceManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractController<Ctxt extends Context> implements IController<Ctxt> {

    protected Logger log = LogProvider.createLogger(this.getClass());
    protected Ctxt context;
    protected ServiceManager manager;

    @Override
    public void start(Ctxt context) {
        this.context = context;
        this.manager = context.getManager();
    }

    @Override
    public void stop() {
        getServices().forEach(AbstractService::stop);
    }

    @Override
    public String getStatusDescription() {
        return "Status description is unimplemented.";
    }

    @Override
    public Double getWorkload() {
        return 0.0;
    }

}
