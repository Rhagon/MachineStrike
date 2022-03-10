package machinestrike.client.console.action.client;

import org.jetbrains.annotations.NotNull;

public interface ClientActionHandler {

    void handle(@NotNull HelpAction action);

    void handle(@NotNull NewGameAction action);

    void handle(@NotNull QuitAction action);

    void handle(@NotNull RedrawAction action);

    void handle(@NotNull SetWindowSizeAction action);

    void handle(@NotNull UnknownCommandAction action);

}
