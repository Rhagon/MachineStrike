package machinestrike.client.console.action;

import org.jetbrains.annotations.NotNull;

public class HelpAction implements ConsoleAction {

    @Override
    public void execute(@NotNull ClientActionHandler handler) {
        handler.handle(this);
    }

}
