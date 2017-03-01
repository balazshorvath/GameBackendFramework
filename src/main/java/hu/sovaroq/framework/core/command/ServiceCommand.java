package hu.sovaroq.framework.core.command;

import hu.sovaroq.framework.controller.base.AbstractController;
import hu.sovaroq.framework.service.base.AbstractService;
import lombok.Data;

/**
 * Created by Oryk on 2017. 03. 01..
 */
@Data
public class ServiceCommand extends ControllerCommand {
    protected final Class<? extends AbstractService> service;
    protected final ServiceCommandType serviceCommandType;


    public ServiceCommand(Class<? extends AbstractController> controller, Class<? extends AbstractService> service, ServiceCommandType type) {
        super(controller, ControllerCommandType.CONFIGURE);
        this.service = service;
        this.serviceCommandType = type;
    }

    public enum ServiceCommandType {
        CREATE,
        RESTART,
        STOP
    }
}
