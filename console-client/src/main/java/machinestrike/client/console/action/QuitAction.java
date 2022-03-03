package machinestrike.client.console.action;

import org.jetbrains.annotations.NotNull;

public class QuitAction implements ConsoleAction {

    @Override
    public void execute(@NotNull ConsoleActionHandler handler) {
        handler.handle(this);
    }

}
