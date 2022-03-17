package machinestrike.client.console.statemachine.client;

import machinestrike.action.Action;
import machinestrike.client.console.action.client.*;
import machinestrike.client.console.input.Command;
import machinestrike.client.console.input.InputHandler;
import machinestrike.action.ActionExecutionFailure;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class ClientInputState<HandlerType extends ClientActionHandler> extends ClientState implements ClientActionHandler {

    private boolean active;

    public ClientInputState(@NotNull ClientStateMachine stateMachine) {
        super(stateMachine);
        this.active = false;
    }

    protected abstract List<Command<? super HandlerType>> commands();

    protected abstract void execute(@NotNull Action<? super HandlerType> action) throws ActionExecutionFailure;

    public void stop(@NotNull ClientState nextState) {
        active = false;
        stateMachine().nextState(nextState);
    }

    protected List<String> help() {
        List<String> help = new ArrayList<>();
        for(Command<? super HandlerType> cmd : commands()) {
            help.add(cmd.syntax());
        }
        return help;
    }

    @Override
    public void exec() {
        InputHandler<HandlerType> input = new InputHandler<>(stateMachine().client().input(), commands());
        active = true;
        for(Action<? super HandlerType> action : input) {
            stateMachine().clearInfo();
            try {
                execute(action);
            } catch (ActionExecutionFailure e) {
                stateMachine().info(e.getMessage());
            }
            stateMachine().client().render();
            if(!active) {
                input.active(false);
            }
        }
    }

    @Override
    public void handle(@NotNull HelpAction action) {
        ArrayList<String> help = new ArrayList<>();
        help.add("Available commands:");
        help.addAll(help());
        stateMachine().info(help);
    }

    @Override
    public void handle(@NotNull RedrawAction action) {
        stateMachine().client().updateUI();
    }

    @Override
    public void handle(@NotNull SetWindowSizeAction action) {
        stateMachine().client().updateWindowSize(action.width(), action.height());
    }

    @Override
    public void handle(@NotNull UnknownCommandAction action) {
        stateMachine().info("Unknown command:\n" + action.command());
    }

}
