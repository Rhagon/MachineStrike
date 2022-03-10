package machinestrike.client.console.action.setup;

import machinestrike.action.Action;
import machinestrike.game.Orientation;
import machinestrike.game.Player;
import machinestrike.game.Point;
import machinestrike.game.rule.RuleViolation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record PlaceMachineAction(@Nullable String machineName,
                                 @NotNull Player player,
                                 @NotNull Orientation orientation,
                                 @NotNull Point position) implements Action<SetupActionHandler> {

    @Override
    public void execute(@NotNull SetupActionHandler handler) throws RuleViolation {
        handler.handle(this);
    }

}
