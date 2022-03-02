package machinestrike.game.rule.moverule;

import machinestrike.debug.Assert;
import machinestrike.game.Game;
import machinestrike.game.action.MoveAction;
import machinestrike.game.machine.Machine;
import org.jetbrains.annotations.NotNull;

public class MachineCanMoveRule implements MoveRule{

    @Override
    public @NotNull String errorMessage() {
        return "The Machine cannot move.";
    }

    @Override
    public boolean test(Game game, MoveAction action) {
        Machine machine = game.board().field(action.origin()).machine();
        Assert.requireNotNull(machine);
        return machine.canMove() || !machine.wasOvercharged();
    }
}
