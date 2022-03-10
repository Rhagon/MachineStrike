package machinestrike.client.console.action.client;

import machinestrike.action.Action;
import machinestrike.game.rule.RuleViolation;
import org.jetbrains.annotations.NotNull;

public record UnknownCommandAction(@NotNull String command) implements Action<ClientActionHandler> {

    @Override
    public void execute(@NotNull ClientActionHandler handler) throws RuleViolation {
        handler.handle(this);
    }

}
