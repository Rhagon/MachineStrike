package machinestrike.game.machine;

import machinestrike.game.Orientation;
import machinestrike.game.Player;
import machinestrike.game.Point;
import machinestrike.game.Trait;
import machinestrike.game.action.AttackAction;
import org.jetbrains.annotations.NotNull;

import java.util.List;
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

    @Override
    public void attack(@NotNull AttackAction action) {

    }

    @Override
    public @NotNull List<Point> attackableFields(@NotNull Point from, @NotNull Orientation orientation) {
        return null;
    }
}
