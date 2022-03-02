package machinestrike.game.statemachine;

import machinestrike.game.action.*;
import org.jetbrains.annotations.NotNull;

public class UninitializedState extends State {

    public UninitializedState() {
        super(null);
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
