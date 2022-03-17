package machinestrike.client.console.action.setup;

import machinestrike.action.Action;
import machinestrike.action.ActionExecutionFailure;
import org.jetbrains.annotations.NotNull;

public class MirrorTerrainAction implements Action<SetupActionHandler> {

    @Override
    public void execute(@NotNull SetupActionHandler handler) throws ActionExecutionFailure {
        handler.handle(this);
    }

}
