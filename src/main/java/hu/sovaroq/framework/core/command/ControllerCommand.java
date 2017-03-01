package hu.sovaroq.framework.core.command;

import hu.sovaroq.framework.controller.base.AbstractController;
import lombok.Data;

/**
 * Created by Oryk on 2017. 03. 01..
 */
@Data
public class ControllerCommand {
    protected final Class<? extends AbstractController> controller;
    protected final ControllerCommandType type;

    public ControllerCommand(Class<? extends AbstractController> controller, ControllerCommandType type) {
        this.controller = controller;
        this.type = type;
    }

    public enum ControllerCommandType {
        CREATE,
        CONFIGURE,
        RESTART,
        STOP
    }
}
