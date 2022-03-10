package machinestrike.client.console.action.setup;

import machinestrike.action.Action;
import machinestrike.game.rule.RuleViolation;
import org.jetbrains.annotations.NotNull;

public class MirrorTerrainAction implements Action<SetupActionHandler> {

    @Override
    public void execute(@NotNull SetupActionHandler handler) throws RuleViolation {
        handler.handle(this);
    }

}
