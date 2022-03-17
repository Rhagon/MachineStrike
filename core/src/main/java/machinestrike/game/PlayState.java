package machinestrike.game;

import machinestrike.action.ActionExecutionFailure;
import machinestrike.game.action.AttackAction;
import machinestrike.game.action.EndTurnAction;
import machinestrike.game.action.MoveAction;
import org.jetbrains.annotations.NotNull;

public class PlayState extends GameState {

    public PlayState(@NotNull Game game) {
        super(game);
    }

    @Override
    public void handle(@NotNull AttackAction action) throws ActionExecutionFailure {
        game().handle(action);
    }

    @Override
    public void handle(@NotNull MoveAction action) throws ActionExecutionFailure {
        game().handle(action);
    }

    @Override
    public void handle(@NotNull EndTurnAction action) throws ActionExecutionFailure {
        game().handle(action);
    }

}
