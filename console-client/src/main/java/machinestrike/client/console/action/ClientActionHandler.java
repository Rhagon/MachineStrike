package machinestrike.client.console.action;

import machinestrike.game.action.GameActionHandler;
import org.jetbrains.annotations.NotNull;

public interface ClientActionHandler extends GameActionHandler {

    void handle(@NotNull HelpAction action);

    void handle(@NotNull QuitAction action);

}
