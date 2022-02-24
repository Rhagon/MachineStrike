package machinestrike.game.machine;

import machinestrike.game.Orientation;
import machinestrike.game.Player;
import machinestrike.game.Trait;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class Melee extends Machine {

    public Melee(@NotNull String name, @NotNull Player player, int victoryPoints, int health, int strength, int moveRange,
                 @NotNull Orientation orientation, @NotNull Armor armor, Set<Trait> traits) {
        super(name, player, victoryPoints, health, strength, moveRange, 1, orientation, armor, traits);
    }

    @Override
    public char descriptor() {
        return player() == Player.BLUE ? 'M' : 'm';
    }

    @Override
    public @NotNull String typeName() {
        return "Melee";
    }
}
