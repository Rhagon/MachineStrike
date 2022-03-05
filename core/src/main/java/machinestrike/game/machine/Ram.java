package machinestrike.game.machine;

import machinestrike.debug.Assert;
import machinestrike.game.Game;
import machinestrike.game.Orientation;
import machinestrike.game.Player;
import machinestrike.game.Trait;
import machinestrike.game.action.AttackAction;
import machinestrike.game.action.MoveAction;
import machinestrike.game.level.Field;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class Ram extends Melee {

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
        Assert.requireNotNull(field());
        Game game = field().board().game();
        Assert.requireNotNull(game);
        Field attackedField = firstAssailableFieldWithMachine();
        Assert.requireNotNull(attackedField);
        performStandardAttack(this, action.origin());
        Machine attackedMachine = attackedField.machine();
        if(attackedMachine != null) {
            attackedMachine.knockBack(orientation());
        }
        MoveAction follow = new MoveAction(action.origin(), attackedField.position(), orientation(), false, true);
        if(game.ruleBook().testMove(game, follow)) {
            Assert.requireNoThrow(() -> game.handle(follow));
        }
    }

}
