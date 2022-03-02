package machinestrike.game.action;

import machinestrike.game.Game;
import machinestrike.game.Point;
import machinestrike.game.rule.RuleViolation;
import org.jetbrains.annotations.NotNull;

public record AttackAction(@NotNull Point origin) implements Action {

    @Override
    public void execute(Game game) throws RuleViolation {
        game.handle(this);
    }
}
