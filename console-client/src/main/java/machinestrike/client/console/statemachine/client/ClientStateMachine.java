package machinestrike.client.console.statemachine.client;

import machinestrike.client.console.ConsoleClient;
import machinestrike.client.console.statemachine.State;
import machinestrike.client.console.statemachine.StateMachine;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

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

    public void info(@NotNull String message) {
        client.info(message);
    }

    public void info(@NotNull List<String> message) {
        StringBuilder builder = new StringBuilder();
        for (String line : message) {
            builder.append(line).append("\n");
        }
        info(builder.toString());
    }

    public void info(@NotNull String header, @NotNull List<String> message) {
        ArrayList<String> info = new ArrayList<>();
        info.add(header);
        info.addAll(message);
        info(info);
    }

    public void clearInfo() {
        client.info("");
    }

}
