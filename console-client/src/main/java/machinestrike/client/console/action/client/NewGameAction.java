package machinestrike.client.console.action.client;

import machinestrike.action.Action;
import machinestrike.util.Point;
import machinestrike.action.ActionExecutionFailure;
import org.jetbrains.annotations.NotNull;

public record NewGameAction(@NotNull Point boardSize) implements Action<ClientActionHandler> {

    @Override
    public void execute(@NotNull ClientActionHandler handler) throws ActionExecutionFailure {
        handler.handle(this);
    }
}
