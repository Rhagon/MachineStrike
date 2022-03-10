package machinestrike.action;

import machinestrike.game.rule.RuleViolation;
import org.jetbrains.annotations.NotNull;

public interface Action<HandlerType> {

    void execute(@NotNull HandlerType handler) throws RuleViolation;

}
