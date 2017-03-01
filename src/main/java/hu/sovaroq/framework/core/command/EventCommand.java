package hu.sovaroq.framework.core.command;

import hu.sovaroq.framework.core.eventbase.IFrameworkEvent;
import lombok.Data;

/**
 * Created by Oryk on 2017. 03. 01..
 */
@Data
public class EventCommand implements FrameworkCommand {
    protected final IFrameworkEvent event;

    public EventCommand(IFrameworkEvent event) {
        this.event = event;
    }
}
