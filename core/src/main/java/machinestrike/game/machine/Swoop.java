package machinestrike.game.machine;

import machinestrike.debug.Assert;
import machinestrike.game.*;
import machinestrike.game.action.AttackAction;
import machinestrike.game.action.MoveAction;
import machinestrike.game.level.Field;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class Swoop extends Melee {

    public Swoop(@NotNull String name, @NotNull Player player, int victoryPoints, int health, int strength,
                 int moveRange, int attackRange, @NotNull Orientation orientation, @NotNull Armor armor, @NotNull Set<Trait> traits) {
        super(name, player, victoryPoints, health, strength, moveRange, attackRange, orientation, armor, traits);
    }

    @Override
    public char descriptor() {
        return player() == Player.BLUE ? 'S' : 's';
    }

    @Override
    public @NotNull String typeName() {
        return "Swoop";
    }

    @Override
    public void attack(@NotNull AttackAction action) {
        Assert.requireNotNull(field());
        Game game = field().board().game();
        Assert.requireNotNull(game);
        Field attackedField = firstAssailableFieldWithMachine();
        Assert.requireNotNull(attackedField);

        Point targetPosition = attackedField.position().add(orientation().add(Orientation.SOUTH).asPoint());
        MoveAction swoop = new MoveAction(action.origin(), targetPosition, orientation(), false, true);
        Assert.requireNoThrow(() -> game.handle(swoop));
        performStandardAttack(this, targetPosition);
    }

}
