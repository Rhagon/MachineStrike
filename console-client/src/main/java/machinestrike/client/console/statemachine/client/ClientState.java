package machinestrike.client.console.statemachine.client;

import machinestrike.client.console.statemachine.State;
import org.jetbrains.annotations.NotNull;

public abstract class ClientState implements State {

    @NotNull
    private final ClientStateMachine stateMachine;

    public ClientState(@NotNull ClientStateMachine stateMachine) {
        this.stateMachine = stateMachine;
    }

    public ClientStateMachine stateMachine() {
        return stateMachine;
    }

}
