package machinestrike.game.rule;

import machinestrike.game.Game;
import machinestrike.game.Orientation;
import machinestrike.game.action.AttackAction;
import machinestrike.game.action.MoveAction;
import machinestrike.game.machine.Machine;
import machinestrike.game.rule.attackrule.AttackRule;
import machinestrike.game.rule.moverule.MoveRule;
import machinestrike.game.rule.strengthrule.StrengthRule;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public record RuleBook(int machinesPerTurn, int requiredVictoryPoints, List<MoveRule> moveRules,
                       List<AttackRule> attackRules, List<StrengthRule> strengthRules) {

    public RuleBook(int machinesPerTurn, int requiredVictoryPoints, List<MoveRule> moveRules,
                    List<AttackRule> attackRules, List<StrengthRule> strengthRules) {
        this.machinesPerTurn = machinesPerTurn;
        this.requiredVictoryPoints = requiredVictoryPoints;
        this.moveRules = Collections.unmodifiableList(moveRules);
        this.attackRules = Collections.unmodifiableList(attackRules);
        this.strengthRules = Collections.unmodifiableList(strengthRules);
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

    /**
     * Strength of a machine might be dependent on the direction of the attack
     * @param machine The machine that the combat strength is calculated for
     * @param direction The absolute direction that the strength is calculated for. For an attacker this will
     *                    typically be the orientation of the attacker itself. For a defender it is the direction that,
     *                    the attack is coming from, so it is the opposite of the orientation of the attacker.
     */
    public int calculateStrength(@NotNull Machine machine, @NotNull Orientation direction, boolean includeArmor) {
        int strength = 0;
        for(StrengthRule rule : strengthRules) {
            strength += rule.getModifier(machine, direction, includeArmor);
        }
        return Math.max(0, strength);
    }

}
