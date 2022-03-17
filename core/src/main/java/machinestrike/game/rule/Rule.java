package machinestrike.game.rule;

import machinestrike.action.ActionExecutionFailure;
import machinestrike.game.Game;
import machinestrike.action.Action;
import org.jetbrains.annotations.NotNull;

public interface Rule<ActionType extends Action<?>> {

    @NotNull String errorMessage();

    boolean test(Game game, ActionType action);

    default void verify(Game game, ActionType action) throws ActionExecutionFailure {
        if(!test(game, action)) {
            throw new ActionExecutionFailure(errorMessage());
        }
    }

}
