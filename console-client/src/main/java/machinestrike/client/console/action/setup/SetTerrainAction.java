package machinestrike.client.console.action.setup;

import machinestrike.action.Action;
import machinestrike.game.Point;
import machinestrike.game.rule.RuleViolation;
import org.jetbrains.annotations.NotNull;

public record SetTerrainAction(@NotNull Point position, @NotNull String terrainName) implements Action<SetupActionHandler> {

    @Override
    public void execute(@NotNull SetupActionHandler handler) throws RuleViolation {
        handler.handle(this);
    }

}
