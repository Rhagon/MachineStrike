package machinestrike.game.statemachine;

import machinestrike.game.Point;
import machinestrike.game.action.*;
import machinestrike.game.machine.Machine;
import org.jetbrains.annotations.NotNull;

public class MachineMovingState extends State{

    @NotNull
    private final Machine machine;
    @NotNull
    private final Point origin;

    public MachineMovingState(@NotNull StateMachine stateMachine, @NotNull Machine machine, @NotNull Point origin) {
        super(stateMachine);
        this.machine = machine;
        this.origin = origin;
    }

    @NotNull
    public Machine machine() {
        return machine;
    }

    @NotNull
    public Point origin() {
        return origin;
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

    }
}
