package machinestrike.game.rule.moverule;

import machinestrike.game.Game;
import machinestrike.game.action.MoveAction;
import machinestrike.game.machine.Machine;
import org.jetbrains.annotations.NotNull;

/**
 * Ensures that origin and destination describe valid fields,
 * there is a machine on the origin field, that belongs to the player on turn
 * and there is no machine on the destination field
 */
public class ValidMoveRule implements MoveRule{

    private static ValidMoveRule instance;

    public static ValidMoveRule instance() {
        if(instance == null) {
            instance = new ValidMoveRule();
        }
        return instance;
    }

    private ValidMoveRule() {
    }

    @Override
    public @NotNull String errorMessage() {
        return "Illegal move";
    }

    @Override
    public boolean test(Game game, MoveAction action) {
        if(!game.board().hasField(action.origin()) || !game.board().hasField(action.destination())) {
            return false;
        }
        Machine machine = game.board().field(action.origin()).machine();
        return machine != null
                && (machine.player() == game.playerOnTurn() || action.virtualMove())
                && game.board().field(action.destination()).machine() == null
                && (!action.sprint() || machine.canAttack());
    }
}
