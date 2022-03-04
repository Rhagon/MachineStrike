package machinestrike.game.action;

import machinestrike.action.Action;
import machinestrike.action.GameActionHandler;
import machinestrike.game.Orientation;
import machinestrike.game.Point;
import machinestrike.game.rule.RuleViolation;
import org.jetbrains.annotations.NotNull;

public record MoveAction<T extends GameActionHandler>(@NotNull Point origin,
                         @NotNull Point destination,
                         @NotNull Orientation orientation,
                         boolean sprint,
                         boolean virtualMove) implements Action<T> {

    public MoveAction(@NotNull Point origin, @NotNull Point destination, @NotNull Orientation orientation, boolean sprint) {
        this(origin, destination, orientation, sprint, false);
    }

    @Override
    public void execute(@NotNull T handler) throws RuleViolation {
        handler.handle(this);
    }

}
