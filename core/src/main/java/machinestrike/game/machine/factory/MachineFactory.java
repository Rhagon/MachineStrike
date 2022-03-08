package machinestrike.game.machine.factory;

import machinestrike.game.Orientation;
import machinestrike.game.Player;
import machinestrike.game.machine.Machine;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface MachineFactory {

    @Nullable
    Machine create(@NotNull String name, @NotNull Player player, @NotNull Orientation orientation);

    @NotNull
    Machine createBurrower(@NotNull Player player, @NotNull Orientation orientation);
}
