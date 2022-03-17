package machinestrike.client.console.statemachine.client;

import machinestrike.action.Action;
import machinestrike.action.ActionExecutionFailure;
import machinestrike.client.console.action.client.NewGameAction;
import machinestrike.client.console.action.client.QuitAction;
import machinestrike.client.console.action.game.ClientGameActionHandler;
import machinestrike.client.console.input.Command;
import machinestrike.client.console.input.game.ClientGameCommandFactory;
import machinestrike.game.action.AttackAction;
import machinestrike.game.action.EndTurnAction;
import machinestrike.game.action.GameActionHandler;
import machinestrike.game.action.MoveAction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

public class GameState extends ClientInputState<ClientGameActionHandler> implements ClientGameActionHandler {

    @Unmodifiable
    @NotNull
    private final List<Command<? super ClientGameActionHandler>> commands;

    public GameState(@NotNull ClientStateMachine stateMachine) {
        super(stateMachine);
        this.commands = ClientGameCommandFactory.instance().create();
    }

    @Override
    protected List<Command<? super ClientGameActionHandler>> commands() {
        return commands;
    }

    @Override
    protected void execute(@NotNull Action<? super ClientGameActionHandler> action) throws ActionExecutionFailure {
        action.execute(this);
    }

    @Override
    public void handle(@NotNull NewGameAction action) {
        stateMachine().client().newGame(action.boardSize());
        stop(new SetupState(stateMachine()));
    }

    @Override
    public void handle(@NotNull QuitAction action) {
        stop(new ClientExitState(stateMachine()));
    }

    private void handleGameAction(@NotNull Action<? super GameActionHandler> action) throws ActionExecutionFailure {
        stateMachine().client().game().execute(action);
        stateMachine().client().updateUI();
    }

    @Override
    public void handle(@NotNull AttackAction action) throws ActionExecutionFailure {
        handleGameAction(action);
    }

    @Override
    public void handle(@NotNull MoveAction action) throws ActionExecutionFailure {
        handleGameAction(action);
    }

    @Override
    public void handle(@NotNull EndTurnAction action) throws ActionExecutionFailure {
        handleGameAction(action);
    }
}
