package machinestrike.game.machine.factory;

import machinestrike.util.Orientation;
import machinestrike.game.Player;
import machinestrike.game.machine.Machine;
import machinestrike.game.machine.MachineKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface MachineFactory {

    @Nullable
    Machine create(@NotNull MachineKey key, @NotNull Player player, @NotNull Orientation orientation);

    @NotNull
    List<MachineKey> keys();

}
