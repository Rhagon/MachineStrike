package machinestrike.game.rule;

import machinestrike.game.Game;
import machinestrike.action.Action;
import org.jetbrains.annotations.NotNull;

public interface Rule<ActionType extends Action> {

    @NotNull String errorMessage();

    boolean test(Game game, ActionType action);

    default void verify(Game game, ActionType action) throws RuleViolation {
        if(!test(game, action)) {
            throw new RuleViolation(errorMessage());
        }
    }

}
