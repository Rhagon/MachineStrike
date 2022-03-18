package machinestrike.game.machine;

import machinestrike.debug.Assert;
import machinestrike.game.Game;
import machinestrike.util.Orientation;
import machinestrike.game.Player;
import machinestrike.game.Trait;
import machinestrike.game.action.MoveAction;
import machinestrike.game.level.Field;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class Ram extends Melee {

    public Ram(@NotNull MachineKey key, @NotNull Player player, int victoryPoints, int health, int strength, int moveRange,
               int attackRange, @NotNull Orientation orientation, @NotNull Armor armor, @NotNull Set<Trait> traits) {
        super(key, player, victoryPoints, health, strength, moveRange, attackRange, orientation, armor, traits);
    }

    @Override
    public void attack() {
        Field field = Assert.requireNotNull(field());
        Game game = field.board().game();
        Assert.requireNotNull(game);
        Field attackedField = firstAssailableFieldWithMachine();
        Assert.requireNotNull(attackedField);
        game.performStandardAttack(this, field.position());
        Machine attackedMachine = attackedField.machine();
        if(attackedMachine != null) {
            game.knockBack(attackedMachine, orientation());
        }
        MoveAction follow = new MoveAction(field.position(), attackedField.position(), orientation(), false, true);
        if(game.ruleBook().testMove(game, follow)) {
            Assert.requireNoThrow(() -> game.move(follow));
        }
    }

}
