package machinestrike.game.machine;

import machinestrike.debug.Assert;
import machinestrike.game.*;
import machinestrike.game.level.Board;
import machinestrike.game.level.Field;
import machinestrike.util.Orientation;
import machinestrike.util.Point;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Melee extends Machine {

    public Melee(@NotNull MachineKey key, @NotNull Player player, int victoryPoints, int health, int strength,
                 int moveRange, int attackRange, @NotNull Orientation orientation, @NotNull Armor armor, Set<Trait> traits) {
        super(key, player, victoryPoints, health, strength, moveRange, attackRange, orientation, armor, traits);
    }

    @Override
    public void attack() {
        Field field = Assert.requireNotNull(field());
        Game game = Assert.requireNotNull(field.board().game());
        game.performStandardAttack(this, field.position());
    }

    @Override
    @NotNull
    @Contract(pure = true)
    public List<Point> assailableFields(@NotNull Point from, @NotNull Orientation orientation) {
        Assert.requireNotNull(field());
        Board board = field().board();
        List<Point> fields = new ArrayList<>();
        Point current = from;
        for(int i = 0; i < attackRange(); ++i) {
            current = current.add(orientation.asPoint());
            if(board.hasField(current)) {
                fields.add(current);
            }
        }
        return fields;
    }

    @Override
    public boolean canCurrentlyPerformAttack() {
        Field f = firstAssailableFieldWithMachine();
        if(f != null) {
            Assert.requireNotNull(f.machine());
            return f.machine().player() == player().opponent();
        }
        return false;
    }
}
