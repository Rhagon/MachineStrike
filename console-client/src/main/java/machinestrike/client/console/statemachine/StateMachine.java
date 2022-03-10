package machinestrike.client.console.statemachine;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class StateMachine {

    @Nullable
    private State currentState;
    @Nullable
    private State nextState;
    private boolean finish;

    public StateMachine() {
        currentState = createStartState();
        nextState = null;
    }

    public void run() {
        currentState = createStartState();
        nextState = null;
        finish = false;
        while(!finish) {
            currentState.exec();
            transition();
        }
    }

    public void finish() {
        this.finish = true;
    }

    public void nextState(@NotNull State nextState) {
        this.nextState = nextState;
    }

    protected abstract State createStartState();

    private void transition() {
        if(nextState != null) {
            currentState = nextState;
            nextState = null;
        }
    }

}
