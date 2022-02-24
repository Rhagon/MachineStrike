package machinestrike.game.machine;

import machinestrike.game.Orientation;
import machinestrike.game.Player;
import machinestrike.game.Trait;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class Ram extends Machine{

    public Ram(@NotNull String name, @NotNull Player player, int victoryPoints, int health, int strength, int moveRange,
               int attackRange, @NotNull Orientation orientation, @NotNull Armor armor, @NotNull Set<Trait> traits) {
        super(name, player, victoryPoints, health, strength, moveRange, attackRange, orientation, armor, traits);
    }

    @Override
    public char descriptor() {
        return player() == Player.BLUE ? 'R' : 'r';
    }

    @Override
    public @NotNull String typeName() {
        return "Ram";
    }
}
