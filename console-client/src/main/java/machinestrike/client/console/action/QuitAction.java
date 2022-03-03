package machinestrike.client.console.action;

import machinestrike.client.console.ConsoleClient;
import machinestrike.game.Game;
import org.jetbrains.annotations.NotNull;

public class QuitAction extends ControlAction {

    public QuitAction(@NotNull ConsoleClient client) {
        super(client);
    }

    @Override
    public void execute(@NotNull Game game) {
        client().handle(this);
    }

}
