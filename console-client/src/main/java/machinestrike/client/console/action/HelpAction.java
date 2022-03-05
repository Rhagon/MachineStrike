package machinestrike.client.console.action;

import machinestrike.action.Action;
import org.jetbrains.annotations.NotNull;

public class HelpAction implements Action<ClientActionHandler> {

    @Override
    public void execute(@NotNull ClientActionHandler handler) {
        handler.handle(this);
    }

}
