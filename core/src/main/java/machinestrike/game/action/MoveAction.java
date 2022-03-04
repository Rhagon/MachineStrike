package machinestrike.game.action;

import machinestrike.game.Orientation;
import machinestrike.game.Point;
import machinestrike.game.rule.RuleViolation;
import org.jetbrains.annotations.NotNull;

public record MoveAction(@NotNull Point origin,
                         @NotNull Point destination,
                         @NotNull Orientation orientation,
                         boolean sprint,
                         boolean virtualMove) implements GameAction {

    public MoveAction(@NotNull Point origin, @NotNull Point destination, @NotNull Orientation orientation, boolean sprint) {
        this(origin, destination, orientation, sprint, false);
    }

    @Override
    public void execute(@NotNull GameActionHandler handler) throws RuleViolation {
        handler.handle(this);
    }

}
