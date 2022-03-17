package machinestrike.client.console.action.setup;

import machinestrike.action.Action;
import machinestrike.action.ActionExecutionFailure;
import org.jetbrains.annotations.NotNull;

public class StartGameAction implements Action<ClientSetupActionHandler> {

    @Override
    public void execute(@NotNull ClientSetupActionHandler handler) throws ActionExecutionFailure {
        handler.handle(this);
    }

}
