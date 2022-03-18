package machinestrike.game.machine;

import machinestrike.util.Orientation;
import machinestrike.game.Player;
import machinestrike.util.Point;
import machinestrike.game.Trait;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

public class Dash extends Machine {

    public Dash(@NotNull MachineKey key, @NotNull Player player, int victoryPoints, int health, int strength, int moveRange,
                int attackRange, @NotNull Orientation orientation, @NotNull Armor armor, @NotNull Set<Trait> traits) {
        super(key, player, victoryPoints, health, strength, moveRange, attackRange, orientation, armor, traits);
    }

    @Override
    public void attack() {
    }

    @Override
    public @NotNull List<Point> assailableFields(@NotNull Point from, @NotNull Orientation orientation) {
        return null;
    }

    @Override
    public boolean canCurrentlyPerformAttack() {
        return false;
    }
}
