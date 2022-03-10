package machinestrike.client.console.action.client;

import machinestrike.client.console.action.*;
import machinestrike.client.console.action.setup.MirrorTerrainAction;
import machinestrike.client.console.action.setup.PlaceMachineAction;
import machinestrike.client.console.action.setup.SetTerrainAction;
import machinestrike.game.action.GameActionHandler;
import org.jetbrains.annotations.NotNull;

public interface ClientActionHandler extends GameActionHandler {

    void handle(@NotNull HelpAction action);

    void handle(@NotNull QuitAction action);

    void handle(@NotNull RedrawAction action);

    void handle(@NotNull SetWindowSizeAction action);

    void handle(@NotNull UnknownCommandAction action);

    void handle(@NotNull NewGameAction action);

    void handle(@NotNull ConfirmationAction action);

    void handle(@NotNull SetTerrainAction action);

    void handle(@NotNull MirrorTerrainAction action);

    void handle(@NotNull PlaceMachineAction action);

}
