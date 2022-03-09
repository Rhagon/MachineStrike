package machinestrike.client.console.statemachine;

import machinestrike.action.Action;
import machinestrike.client.console.action.ClientActionHandler;
import machinestrike.client.console.action.ConfirmationAction;
import machinestrike.client.console.action.HelpAction;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ConfirmationState extends State {

    private State parentState;
    @NotNull
    private final State confirmState;
    @NotNull
    private final String confirmationMessage;

    public ConfirmationState(@NotNull StateMachine stateMachine, @NotNull State confirmState, @NotNull String confirmationMessage) {
        super(stateMachine);
        this.confirmState = confirmState;
        this.confirmationMessage = confirmationMessage;
    }

    @Override
    public void defaultAction(@NotNull Action<? super ClientActionHandler> action) {
        stateMachine().enter(parentState);
    }

    @Override
    @NotNull
    public List<String> help() {
        return new ArrayList<>(List.of(confirmationMessage + "\n Type yes (y) or no (n)"));
    }

    @Override
    public void beforeEnter() {
        parentState = stateMachine().currentState();
    }

    @Override
    public void onEnter() {
        stateMachine().info(confirmationMessage);
    }

    @Override
    public void onExit() {
        stateMachine().clearInfo();
    }

    @Override
    public void handle(@NotNull ConfirmationAction action) {
        if(action.confirm()) {
            stateMachine().enter(confirmState);
        } else {
            defaultAction(action);
        }
    }

    @Override
    public void handle(@NotNull HelpAction action) {
        stateMachine().info(help());
    }


}
