package machinestrike.game.statemachine;

import machinestrike.game.Game;
import machinestrike.game.action.Action;
import machinestrike.game.rule.RuleViolation;
import org.jetbrains.annotations.NotNull;

public class StateMachine {

    @NotNull
    private final Game game;
    @NotNull
    private State currentState;

    public StateMachine(@NotNull Game game) {
        this.game = game;
        currentState = new UninitializedState();
    }

    @NotNull
    public Game game() {
        return game;
    }

    public void changeState(@NotNull State state) {
        currentState.onExit();
        state.beforeEnter();
        currentState = state;
        currentState.onEnter();
    }

    @NotNull
    public State currentState() {
        return currentState;
    }

    public void execute(@NotNull Action action) throws RuleViolation {
        action.execute(currentState);
    }

}
