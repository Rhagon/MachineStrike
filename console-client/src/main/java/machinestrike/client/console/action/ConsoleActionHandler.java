package machinestrike.client.console.action;

import org.jetbrains.annotations.NotNull;

public interface ConsoleActionHandler {

    void handle(@NotNull HelpAction action);

    void handle(@NotNull QuitAction action);

}
