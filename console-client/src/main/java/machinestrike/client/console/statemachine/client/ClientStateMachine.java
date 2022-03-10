package machinestrike.client.console.statemachine.client;

import machinestrike.client.console.ConsoleClient;
import machinestrike.client.console.statemachine.State;
import machinestrike.client.console.statemachine.StateMachine;
import org.jetbrains.annotations.NotNull;

public class ClientStateMachine extends StateMachine {

    @NotNull
    private final ConsoleClient client;

    public ClientStateMachine(@NotNull ConsoleClient client) {
        super();
        this.client = client;
    }

    @NotNull
    public ConsoleClient client() {
        return client;
    }

    @Override
    protected State createStartState() {
        return new ClientStartState(this);
    }

}
