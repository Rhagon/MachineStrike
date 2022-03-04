package machinestrike.game.rule.attackrule;

import machinestrike.game.Game;
import machinestrike.game.action.AttackAction;
import machinestrike.game.machine.Machine;
import org.jetbrains.annotations.NotNull;

public class ValidAttackRule implements AttackRule {

    private static ValidAttackRule instance;

    public static ValidAttackRule instance() {
        if(instance == null) {
            instance = new ValidAttackRule();
        }
        return instance;
    }

    private ValidAttackRule() {
    }

    @Override
    public @NotNull String errorMessage() {
        return "Illegal attack";
    }

    @Override
    public boolean test(Game game, AttackAction<?> action) {
        if(!game.board().hasField(action.origin())) {
            return false;
        }
        Machine machine = game.board().field(action.origin()).machine();
        return machine != null
                && machine.player() == game.playerOnTurn();
    }

}
