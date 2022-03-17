package machinestrike.game;

import machinestrike.action.ActionExecutionFailure;
import machinestrike.game.action.AttackAction;
import machinestrike.game.action.EndTurnAction;
import machinestrike.game.action.MoveAction;
import org.jetbrains.annotations.NotNull;

public class PlaceState extends GameState {

    public PlaceState(@NotNull Game game) {
        super(game);
    }

    public void defaultAction() throws ActionExecutionFailure {
        throw new ActionExecutionFailure("You cannot do that right now.");
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
