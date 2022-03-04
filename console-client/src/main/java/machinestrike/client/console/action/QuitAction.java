package machinestrike.client.console.action;

import org.jetbrains.annotations.NotNull;

public class QuitAction implements ConsoleAction {

    @Override
    public void execute(@NotNull ClientActionHandler handler) {
        handler.handle(this);
    }

}
