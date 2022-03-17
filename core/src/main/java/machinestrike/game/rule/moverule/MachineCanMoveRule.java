package machinestrike.game.rule.moverule;

import machinestrike.debug.Assert;
import machinestrike.game.Game;
import machinestrike.game.action.MoveAction;
import machinestrike.game.machine.Machine;
import machinestrike.game.machine.MachineState;
import org.jetbrains.annotations.NotNull;

public class MachineCanMoveRule implements MoveRule{

    private static MachineCanMoveRule instance;

    public static MachineCanMoveRule instance() {
        if(instance == null) {
            instance = new MachineCanMoveRule();
        }
        return instance;
    }

    private MachineCanMoveRule() {
    }

    @Override
    public @NotNull String errorMessage() {
        return "The Machine cannot move.";
    }

    @Override
    public boolean test(Game game, MoveAction action) {
        Machine machine = game.board().field(action.origin()).machine();
        Assert.requireNotNull(machine);
        MachineState state = game.machineState(machine);
        if(state == null || action.virtualMove()) {
            return true;
        }
        return (state.canMove && (!action.sprint() || state.canAttack)) //If the machine has not moved nor attacked yet, it can sprint
                || (!state.canMove && !state.wasOvercharged && !action.sprint()); //If the machine has moved yet and was not overcharged, it can move, but it cannot sprint.
    }
}
