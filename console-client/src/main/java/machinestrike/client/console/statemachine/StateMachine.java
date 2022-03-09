package machinestrike.client.console.statemachine;

import machinestrike.action.Action;
import machinestrike.client.console.ConsoleClient;
import machinestrike.client.console.action.ClientActionHandler;
import machinestrike.game.rule.RuleViolation;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class StateMachine {

    @NotNull
    private State currentState;
    @NotNull
    private final ConsoleClient client;

    public StateMachine(@NotNull ConsoleClient client) {
        currentState = new State(this);
        this.client = client;
    }

    @NotNull
    public ConsoleClient client() {
        return client;
    }

    @NotNull
    public State currentState() {
        return currentState;
    }

    public void handle(@NotNull Action<? super ClientActionHandler> action) throws RuleViolation {
        action.execute(currentState);
    }

    public void enter(@NotNull State newState) {
        currentState.onExit();
        newState.beforeEnter();
        currentState = newState;
        currentState.onEnter();
    }

    public void info(@NotNull String message) {
        client.info(message);
    }

    public void info(@NotNull List<String> message) {
        StringBuilder builder = new StringBuilder();
        for(String line : message) {
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
