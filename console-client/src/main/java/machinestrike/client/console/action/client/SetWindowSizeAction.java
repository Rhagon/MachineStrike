package machinestrike.client.console.action.client;

import machinestrike.action.Action;
import org.jetbrains.annotations.NotNull;

public record SetWindowSizeAction(int width, int height) implements Action<ClientActionHandler> {

    @Override
    public void execute(@NotNull ClientActionHandler handler) {
        handler.handle(this);
    }

}
