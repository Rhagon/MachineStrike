package machinestrike.client.console.action.setup;

import machinestrike.action.Action;
import machinestrike.action.ActionExecutionFailure;
import machinestrike.game.Orientation;
import machinestrike.game.Player;
import machinestrike.game.Point;
import machinestrike.game.machine.MachineKey;
import org.jetbrains.annotations.NotNull;

public record PlaceMachineAction(@NotNull MachineKey machineKey,
                                 @NotNull Player player,
                                 @NotNull Orientation orientation,
                                 @NotNull Point position) implements Action<SetupActionHandler> {

    @Override
    public void execute(@NotNull SetupActionHandler handler) throws ActionExecutionFailure {
        handler.handle(this);
    }

}
