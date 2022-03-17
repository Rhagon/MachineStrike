package machinestrike.game.action;

import machinestrike.action.Action;
import machinestrike.action.ActionExecutionFailure;
import org.jetbrains.annotations.NotNull;

public class EndTurnAction implements Action<GameActionHandler> {

    @Override
    public void execute(@NotNull GameActionHandler handler) throws ActionExecutionFailure {
        handler.handle(this);
    }

}