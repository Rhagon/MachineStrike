package machinestrike.game.action;

import machinestrike.game.Game;
import machinestrike.game.Orientation;
import machinestrike.game.Point;
import machinestrike.game.rule.RuleViolation;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public record MoveAction(@NotNull Point origin,
                         @NotNull Point destination,
                         @NotNull Orientation orientation,
                         boolean sprint) implements Action {

    @Override
    public void execute(@NotNull Game game) throws RuleViolation {
        game.handle(this);
    }

    @Override
    @Contract(pure = true)
    public boolean needsActiveGame() {
        return true;
    }

}
