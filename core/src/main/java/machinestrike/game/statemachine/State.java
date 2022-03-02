package machinestrike.game.statemachine;

import machinestrike.game.Game;
import machinestrike.game.action.*;
import machinestrike.game.level.Board;
import machinestrike.game.rule.RuleViolation;
import org.jetbrains.annotations.NotNull;

public abstract class State {

    @NotNull
    private final StateMachine stateMachine;

    public State(@NotNull StateMachine stateMachine) {
        this.stateMachine = stateMachine;
    }

    @NotNull
    public StateMachine stateMachine() {
        return stateMachine;
    }

    public abstract void handle(@NotNull AttackAction action) throws RuleViolation;

    public abstract void handle(@NotNull DropAction action) throws RuleViolation;

    public abstract void handle(@NotNull MoveAction action) throws RuleViolation;

    public abstract void handle(@NotNull OverchargeAction action) throws RuleViolation;

    public abstract void handle(@NotNull SelectAction action) throws RuleViolation;

    @NotNull
    protected Game game() {
        return stateMachine.game();
    }

    @NotNull
    protected Board board() {
        return stateMachine.game().board();
    }

    /**
     * Gets called before the state machine switches into this state
     */
    public void beforeEnter() {

    }

    /**
     * Gets called after the state machined switched into this state
     */
    public void onEnter() {

    }

    /**
     * Gets called before the state machine switches to a new state
     */
    public void onExit() {

    }

}
