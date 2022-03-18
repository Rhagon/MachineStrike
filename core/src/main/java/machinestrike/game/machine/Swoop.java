package machinestrike.game.machine;

import machinestrike.debug.Assert;
import machinestrike.game.*;
import machinestrike.game.action.MoveAction;
import machinestrike.game.level.Field;
import machinestrike.util.Orientation;
import machinestrike.util.Point;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class Swoop extends Melee {

    private static Set<Trait> addSwoopTraits(Set<Trait> traits) {
        HashSet<Trait> modified = new HashSet<>(traits);
        modified.add(Machine.IGNORE_MOVE_IMPEDIMENT);
        return modified;
    }

    public Swoop(@NotNull MachineKey key, @NotNull Player player, int victoryPoints, int health, int strength,
                 int moveRange, int attackRange, @NotNull Orientation orientation, @NotNull Armor armor, @NotNull Set<Trait> traits) {
        super(key, player, victoryPoints, health, strength, moveRange, attackRange, orientation, armor, addSwoopTraits(traits));
    }

    @Override
    public void attack() {
        Assert.requireNotNull(field());
        Game game = field().board().game();
        Assert.requireNotNull(game);
        Field attackedField = firstAssailableFieldWithMachine();
        Assert.requireNotNull(attackedField);

        Point targetPosition = attackedField.position().add(orientation().add(Orientation.SOUTH).asPoint());
        MoveAction swoop = new MoveAction(field().position(), targetPosition, orientation(), false, true);
        Assert.requireNoThrow(() -> game.move(swoop));
        game.performStandardAttack(this, targetPosition);
    }

}
