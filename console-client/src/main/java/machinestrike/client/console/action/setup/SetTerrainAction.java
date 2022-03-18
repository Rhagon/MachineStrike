package machinestrike.client.console.action.setup;

import machinestrike.action.Action;
import machinestrike.util.Point;
import machinestrike.action.ActionExecutionFailure;
import org.jetbrains.annotations.NotNull;

public record SetTerrainAction(@NotNull Point position, @NotNull String terrainName) implements Action<SetupActionHandler> {

    @Override
    public void execute(@NotNull SetupActionHandler handler) throws ActionExecutionFailure {
        handler.handle(this);
    }

}
