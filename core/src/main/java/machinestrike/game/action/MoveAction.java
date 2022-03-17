package machinestrike.game.action;

import machinestrike.action.Action;
import machinestrike.game.Orientation;
import machinestrike.game.Point;
import machinestrike.action.ActionExecutionFailure;
import org.jetbrains.annotations.NotNull;

public record MoveAction(@NotNull Point origin,
                         @NotNull Point destination,
                         @NotNull Orientation orientation,
                         boolean sprint,
                         boolean virtualMove) implements Action<GameActionHandler> {

    public MoveAction(@NotNull Point origin, @NotNull Point destination, @NotNull Orientation orientation, boolean sprint) {
        this(origin, destination, orientation, sprint, false);
    }

    @Override
    public void execute(@NotNull GameActionHandler handler) throws ActionExecutionFailure {
        handler.handle(this);
    }

}
