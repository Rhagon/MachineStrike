package machinestrike.client.console.action;

import machinestrike.action.Action;
import machinestrike.game.Point;
import machinestrike.game.rule.RuleViolation;
import org.jetbrains.annotations.NotNull;

public record SetTerrainAction(@NotNull Point position, @NotNull String terrainName) implements Action<ClientActionHandler> {

    @Override
    public void execute(@NotNull ClientActionHandler handler) throws RuleViolation {
        handler.handle(this);
    }

}
