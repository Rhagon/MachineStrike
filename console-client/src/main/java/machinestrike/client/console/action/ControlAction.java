package machinestrike.client.console.action;

import machinestrike.client.console.ConsoleClient;
import machinestrike.game.action.Action;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public abstract class ControlAction implements Action {

    @NotNull
    private final ConsoleClient client;

    public ControlAction(@NotNull ConsoleClient client) {
        this.client = client;
    }

    protected ConsoleClient client() {
        return client;
    }

    @Override
    @Contract(pure = true)
    public boolean needsActiveGame() {
        return false;
    }
}
