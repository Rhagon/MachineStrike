package machinestrike.game.rule;

import machinestrike.game.Game;
import machinestrike.game.action.AttackAction;
import machinestrike.game.action.MoveAction;
import machinestrike.game.rule.attackrule.AttackRule;
import machinestrike.game.rule.moverule.MoveRule;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public record RuleBook(List<MoveRule> moveRules, List<AttackRule> attackRules) {

    public RuleBook(List<MoveRule> moveRules, List<AttackRule> attackRules) {
        this.moveRules = Collections.unmodifiableList(moveRules);
        this.attackRules = attackRules;
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

    public boolean testAttack(@NotNull Game game, @NotNull AttackAction action) {
        for(AttackRule rule : attackRules) {
            if(!rule.test(game, action)) {
                return false;
            }
        }
        return true;
    }

    public void verifyAttack(@NotNull Game game, @NotNull AttackAction action) throws RuleViolation {
        for(AttackRule rule : attackRules) {
            rule.verify(game, action);
        }
    }

}
