package machinestrike.client.console.statemachine;

import machinestrike.action.Action;
import machinestrike.client.console.action.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class DefaultState extends State {

    public DefaultState(@NotNull StateMachine stateMachine) {
        super(stateMachine);
    }

    @Override
    public void defaultAction(@NotNull Action<? super ClientActionHandler> action) {
        stateMachine().info("You cannot do that right now.");
    }

    @Override
    @NotNull
    public List<String> help() {
        List<String> h = super.help();
        h.add("help");
        h.add("quit");
        h.add("redraw");
        h.add("set window size to <width> <height>");
        return h;
    }

    @Override
    public void handle(@NotNull HelpAction action) {
        stateMachine().info("Available commands:", help());
    }

    @Override
    public void handle(@NotNull QuitAction action) {
        stateMachine().client().quit();
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
