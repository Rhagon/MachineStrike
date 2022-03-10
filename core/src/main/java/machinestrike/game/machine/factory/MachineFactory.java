package machinestrike.game.machine.factory;

import machinestrike.game.Orientation;
import machinestrike.game.Player;
import machinestrike.game.machine.Machine;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface MachineFactory {

    @Nullable
    Machine forName(@NotNull String machineName, @NotNull Player player, @NotNull Orientation orientation);

    @NotNull
    List<String> names();

    @NotNull
    Machine createBurrower(@NotNull Player player, @NotNull Orientation orientation);

    @NotNull
    Machine createScrapper(@NotNull Player player, @NotNull Orientation orientation);

}
