package machinestrike.game.machine;

import machinestrike.debug.Assert;
import machinestrike.game.Orientation;
import machinestrike.game.Player;
import machinestrike.game.Point;
import machinestrike.game.Trait;
import machinestrike.game.action.AttackAction;
import machinestrike.game.level.Board;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

public class Melee extends Machine {

    public Melee(@NotNull String name, @NotNull Player player, int victoryPoints, int health, int strength,
                 int moveRange, @NotNull Orientation orientation, @NotNull Armor armor, Set<Trait> traits) {
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

    @Override
    public void attack(@NotNull AttackAction action) {

    }

    @Override
    public @NotNull List<Point> attackableFields(@NotNull Point from, @NotNull Orientation orientation) {
        Assert.requireNotNull(field());
        Board board = field().board();

        return null;
    }
}
