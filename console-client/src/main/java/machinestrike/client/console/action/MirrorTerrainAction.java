package machinestrike.client.console.action;

import machinestrike.action.Action;
import machinestrike.game.rule.RuleViolation;
import org.jetbrains.annotations.NotNull;

public class MirrorTerrainAction implements Action<ClientActionHandler> {

    @Override
    public void execute(@NotNull ClientActionHandler handler) throws RuleViolation {
        handler.handle(this);
    }

}
