package machinestrike.client.console.statemachine.client;

import machinestrike.action.Action;
import machinestrike.client.console.action.client.NewGameAction;
import machinestrike.client.console.action.client.QuitAction;
import machinestrike.client.console.action.game.ClientGameActionHandler;
import machinestrike.client.console.input.Command;
import machinestrike.client.console.input.game.ClientGameCommandFactory;
import machinestrike.game.action.AttackAction;
import machinestrike.game.action.EndTurnAction;
import machinestrike.game.action.MoveAction;
import machinestrike.game.rule.RuleViolation;
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
    protected void execute(@NotNull Action<? super ClientGameActionHandler> action) throws RuleViolation {
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

    @Override
    public void handle(@NotNull AttackAction action) throws RuleViolation {
        stateMachine().client().game().handle(action);
        stateMachine().client().updateUI();
    }

    @Override
    public void handle(@NotNull MoveAction action) throws RuleViolation {
        stateMachine().client().game().handle(action);
        stateMachine().client().updateUI();
    }

    @Override
    public void handle(@NotNull EndTurnAction action) throws RuleViolation {
        stateMachine().client().game().handle(action);
        stateMachine().client().updateUI();
    }
}
