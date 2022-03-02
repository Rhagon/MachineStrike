package machinestrike.game.action;

import machinestrike.game.Orientation;
import machinestrike.game.Point;
import machinestrike.game.rule.RuleViolation;
import machinestrike.game.statemachine.State;
import org.jetbrains.annotations.NotNull;

public record MoveAction(@NotNull Point origin,
                         @NotNull Point destination,
                         @NotNull Orientation orientation) implements Action {

    @Override
    public void execute(State state) throws RuleViolation {
        state.handle(this);
    }

}
