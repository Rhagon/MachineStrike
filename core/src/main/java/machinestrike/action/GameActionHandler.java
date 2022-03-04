package machinestrike.action;

import machinestrike.game.action.AttackAction;
import machinestrike.game.action.MoveAction;
import machinestrike.game.rule.RuleViolation;
import org.jetbrains.annotations.NotNull;

public interface GameActionHandler {

    void handle(@NotNull AttackAction<?> action) throws RuleViolation;

    void handle(@NotNull MoveAction<?> action) throws RuleViolation;

}
