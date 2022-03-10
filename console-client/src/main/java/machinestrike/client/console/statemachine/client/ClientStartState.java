package machinestrike.client.console.statemachine.client;

import org.jetbrains.annotations.NotNull;

public class ClientStartState extends ClientState {

    public ClientStartState(@NotNull ClientStateMachine stateMachine) {
        super(stateMachine);
    }

    @Override
    public void exec() {
        stateMachine().nextState(new SetupState(stateMachine()));
    }

}
