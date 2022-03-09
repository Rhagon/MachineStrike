package machinestrike.client.console.statemachine;

import machinestrike.action.Action;
import machinestrike.client.console.action.*;
import machinestrike.game.action.AttackAction;
import machinestrike.game.action.MoveAction;
import machinestrike.game.rule.RuleViolation;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class State implements ClientActionHandler {

    private final StateMachine stateMachine;

    public State(@NotNull StateMachine stateMachine) {
        this.stateMachine = stateMachine;
    }

    public StateMachine stateMachine() {
        return stateMachine;
    }

    public void defaultAction(@NotNull Action<? super ClientActionHandler> action) {
    }

    @NotNull
    public List<String> help() {
        return new ArrayList<>();
    }

    public void beforeEnter() {

    }

    public void onEnter() {

    }

    public void onExit() {

    }

    @Override
    public void handle(@NotNull HelpAction action) {
        defaultAction(action);
    }

    @Override
    public void handle(@NotNull QuitAction action) {
        defaultAction(action);
    }

    @Override
    public void handle(@NotNull RedrawAction action) {
        defaultAction(action);
    }

    @Override
    public void handle(@NotNull SetWindowSizeAction action) {
        defaultAction(action);
    }

    @Override
    public void handle(@NotNull UnknownCommandAction action) {
        defaultAction(action);
    }

    @Override
    public void handle(@NotNull NewGameAction action) {
        defaultAction(action);
    }

    @Override
    public void handle(@NotNull ConfirmationAction action) {
        defaultAction(action);
    }

    @Override
    public void handle(@NotNull SetTerrainAction action) {
        defaultAction(action);
    }

    @Override
    public void handle(@NotNull MirrorTerrainAction action) {
        defaultAction(action);
    }

    @Override
    public void handle(@NotNull PlaceMachineAction action) {
        defaultAction(action);
    }

    @Override
    public void handle(@NotNull AttackAction action) throws RuleViolation {
        defaultAction(action);
    }

    @Override
    public void handle(@NotNull MoveAction action) throws RuleViolation {
        defaultAction(action);
    }
}
