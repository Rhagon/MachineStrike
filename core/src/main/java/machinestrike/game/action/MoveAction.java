package machinestrike.game.action;

import machinestrike.game.Orientation;
import machinestrike.game.Point;
import machinestrike.game.rule.RuleViolation;
import org.jetbrains.annotations.NotNull;

public record MoveAction(@NotNull Point origin,
                         @NotNull Point destination,
                         @NotNull Orientation orientation,
                         boolean sprint) implements GameAction {

    @Override
    public void execute(@NotNull GameActionHandler handler) throws RuleViolation {
        handler.handle(this);
    }

}
