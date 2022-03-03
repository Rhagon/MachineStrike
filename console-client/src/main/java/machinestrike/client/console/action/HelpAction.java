package machinestrike.client.console.action;

import org.jetbrains.annotations.NotNull;

public class HelpAction implements ConsoleAction {

    @Override
    public void execute(@NotNull ConsoleActionHandler handler) {
        handler.handle(this);
    }

}
