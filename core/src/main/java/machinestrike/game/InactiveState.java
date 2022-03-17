package machinestrike.game;

import machinestrike.game.action.AttackAction;
import machinestrike.game.action.EndTurnAction;
import machinestrike.game.action.MoveAction;
import machinestrike.action.ActionExecutionFailure;
import org.jetbrains.annotations.NotNull;

public class InactiveState extends GameState {

    public InactiveState(@NotNull Game game) {
        super(game);
    }

    private void defaultAction() throws ActionExecutionFailure {
        throw new ActionExecutionFailure("The game is not running.");
    }

    @Override
    public void handle(@NotNull AttackAction action) throws ActionExecutionFailure {
        defaultAction();
    }

    @Override
    public void handle(@NotNull MoveAction action) throws ActionExecutionFailure {
        defaultAction();
    }

    @Override
    public void handle(@NotNull EndTurnAction action) throws ActionExecutionFailure {
        defaultAction();
    }

}
