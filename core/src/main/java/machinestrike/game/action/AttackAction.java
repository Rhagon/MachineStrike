package machinestrike.game.action;

import machinestrike.action.Action;
import machinestrike.game.Point;
import machinestrike.game.rule.RuleViolation;
import org.jetbrains.annotations.NotNull;

public record AttackAction(@NotNull Point origin) implements Action<GameActionHandler> {

    @Override
    public void execute(@NotNull GameActionHandler handler) throws RuleViolation {
        handler.handle(this);
    }

}
