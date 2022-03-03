package machinestrike.game.rule.factory;

import machinestrike.game.rule.RuleBook;
import machinestrike.game.rule.attackrule.AttackRule;
import machinestrike.game.rule.attackrule.MachineCanAttackRule;
import machinestrike.game.rule.attackrule.ValidAttackRule;
import machinestrike.game.rule.moverule.*;
import machinestrike.game.rule.strengthrule.StrengthRule;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DefaultRuleBookFactory implements RuleBookFactory {

    private static DefaultRuleBookFactory instance;

    public static DefaultRuleBookFactory instance() {
        if(instance == null) {
            instance = new DefaultRuleBookFactory();
        }
        return instance;
    }

    private DefaultRuleBookFactory() {
    }

    @NotNull
    public RuleBook createRuleBook() {

        List<MoveRule> moveRules = List.of(
                GameNotOverRule.moveInstance(),
                ValidMoveRule.instance(),
                MachineCanMoveRule.instance(),
                ChasmNoGroundedRule.instance(),
                CanReachDestinationRule.instance());

        List<AttackRule> attackRules = List.of(
                GameNotOverRule.attackInstance(),
                ValidAttackRule.instance(),
                MachineCanAttackRule.instance());

        List<StrengthRule> strengthRules = List.of();

        return new RuleBook(2, 7, moveRules, attackRules, strengthRules);
    }

}
