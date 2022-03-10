package machinestrike.client.console.action.setup;

import org.jetbrains.annotations.NotNull;

public interface SetupActionHandler {

    void handle(@NotNull MirrorTerrainAction action);

    void handle(@NotNull PlaceMachineAction action);

    void handle(@NotNull SetTerrainAction action);

}
