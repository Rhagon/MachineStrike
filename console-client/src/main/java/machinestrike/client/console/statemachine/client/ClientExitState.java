package machinestrike.client.console.statemachine.client;

import org.jetbrains.annotations.NotNull;

public class ClientExitState extends ClientState {

    public ClientExitState(@NotNull ClientStateMachine stateMachine) {
        super(stateMachine);
    }

    @Override
    public void exec() {
        stateMachine().finish();
    }

}
