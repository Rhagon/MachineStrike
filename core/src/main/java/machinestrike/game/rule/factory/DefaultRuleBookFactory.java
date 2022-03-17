package machinestrike.game.rule.factory;

import machinestrike.game.Game;
import machinestrike.game.Player;
import machinestrike.game.level.Field;
import machinestrike.game.machine.Machine;
import machinestrike.game.rule.RuleBook;
import machinestrike.game.rule.attackrule.AttackRule;
import machinestrike.game.rule.attackrule.MachineCanAttackRule;
import machinestrike.game.rule.attackrule.ValidAttackRule;
import machinestrike.game.rule.moverule.*;
import machinestrike.game.rule.strengthrule.ArmorStrengthRule;
import machinestrike.game.rule.strengthrule.BaseStrengthRule;
import machinestrike.game.rule.strengthrule.StrengthRule;
import machinestrike.game.rule.strengthrule.TerrainStrengthRule;
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
                ValidMoveRule.instance(),
                MachineCanMoveRule.instance(),
                ChasmNoGroundedRule.instance(),
                CanReachDestinationRule.instance());

        List<AttackRule> attackRules = List.of(
                ValidAttackRule.instance(),
                MachineCanAttackRule.instance());

        List<StrengthRule> strengthRules = List.of(
                BaseStrengthRule.instance(),
                ArmorStrengthRule.instance(),
                TerrainStrengthRule.instance());

        return new RuleBook(2, 7, 2, moveRules, attackRules, strengthRules, this::defaultWinCondition);
    }

    public boolean defaultWinCondition(@NotNull RuleBook ruleBook, @NotNull Game game, @NotNull Player player) {
        if(game.victoryPoints(player) >= ruleBook.requiredVictoryPoints()) {
            return true; //Player has the required amount of victory points.
        }
        for(Field field : game.board()) {
            Machine m = field.machine();
            if(m != null) {
                if (m.player() == player.opponent()) {
                    return false; //Opponent still has at least one machine.
                }
            }
        }
        return true; //Opponent does not have any machines left.
    }

}
