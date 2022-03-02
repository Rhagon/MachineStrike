package machinestrike.game.statemachine;

import machinestrike.game.Player;
import machinestrike.game.Point;
import machinestrike.game.action.*;
import machinestrike.game.level.Field;
import machinestrike.game.machine.Machine;
import org.jetbrains.annotations.NotNull;

public class MachineSelectionState extends State {

    public MachineSelectionState(@NotNull StateMachine stateMachine) {
        super(stateMachine);
    }

    @Override
    public void handle(@NotNull AttackAction action) {

    }

    @Override
    public void handle(@NotNull DropAction action) {

    }

    @Override
    public void handle(@NotNull MoveAction action) {

    }

    @Override
    public void handle(@NotNull OverchargeAction action) {

    }

    @Override
    public void handle(@NotNull SelectAction action) {
        Player player = game().playerOnTurn();
        if(action.player() == player) {
            Point origin = action.position();
            Field originField = board().field(origin);
            Machine machine = originField.machine();
            if(machine != null && machine.player() == player) {
                stateMachine().changeState(new MachineMovingState(stateMachine(), machine, origin));
            }
        }
    }

}
