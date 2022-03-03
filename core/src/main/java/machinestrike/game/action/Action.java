package machinestrike.game.action;

import machinestrike.game.Game;
import machinestrike.game.rule.RuleViolation;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface Action {

    void execute(@NotNull Game game) throws RuleViolation;

    @Contract(pure = true)
    boolean needsActiveGame();

}
