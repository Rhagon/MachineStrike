package machinestrike.game.rule.attackrule;

import machinestrike.debug.Assert;
import machinestrike.game.Game;
import machinestrike.game.action.AttackAction;
import machinestrike.game.machine.Machine;
import org.jetbrains.annotations.NotNull;

public class MachineCanAttackRule implements AttackRule {

    private static MachineCanAttackRule instance;

    public static MachineCanAttackRule instance() {
        if(instance == null) {
            instance = new MachineCanAttackRule();
        }
        return instance;
    }

    private MachineCanAttackRule() {
    }

    @Override
    public @NotNull String errorMessage() {
        return "The machine cannot attack.";
    }

    @Override
    public boolean test(Game game, AttackAction action) {
        Machine machine = game.board().field(action.origin()).machine();
        Assert.requireNotNull(machine);
        if(!machine.canAttack() && machine.wasOvercharged()) {
            return false;
        }
        return machine.canCurrentlyPerformAttack();
    }
}
