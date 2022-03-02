package machinestrike.game.rule.factory;

import machinestrike.game.rule.moverule.*;
import machinestrike.game.rule.RuleBook;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DefaultRuleBookFactory {

    @NotNull
    public RuleBook createRuleBook() {
        List<MoveRule> moveRules = List.of(
                new ValidMoveRule(),
                new MachineCanMoveRule(),
                new ValidSprintRule(),
                ChasmNoGroundedRule.instance(),
                new CanReachDestinationRule());
        return new RuleBook(moveRules);
    }

}
