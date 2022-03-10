package machinestrike.game.rule.factory;

import machinestrike.game.rule.RuleBook;
import org.jetbrains.annotations.NotNull;

public interface RuleBookFactory {

    @NotNull
    RuleBook createRuleBook();

}
