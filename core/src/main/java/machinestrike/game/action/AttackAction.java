package machinestrike.game.action;

import machinestrike.action.Action;
import machinestrike.game.Point;
import machinestrike.game.rule.RuleViolation;
import org.jetbrains.annotations.NotNull;

public record AttackAction<T extends GameActionHandler>(@NotNull Point origin) implements Action<T> {

    @Override
    public void execute(@NotNull T handler) throws RuleViolation {
        handler.handle(this);
    }

}
