package machinestrike.game.rule;

import machinestrike.game.Game;
import machinestrike.game.Point;
import machinestrike.game.action.MoveAction;
import machinestrike.game.machine.Machine;
import machinestrike.game.rule.moverule.MoveRule;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public record RuleBook(List<MoveRule> moveRules) {

    public RuleBook(List<MoveRule> moveRules) {
        this.moveRules = Collections.unmodifiableList(moveRules);
    }

    public boolean testMove(@NotNull Game game, @NotNull MoveAction action) {
        for(MoveRule rule : moveRules) {
            if(!rule.test(game, action)) {
                return false;
            }
        }
        return true;
    }

    public void verifyMove(@NotNull Game game, @NotNull MoveAction action) throws RuleViolation {
        for(MoveRule rule : moveRules) {
            rule.verify(game, action);
        }
    }

}
