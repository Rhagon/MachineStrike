package machinestrike.game.rule.strengthrule;

import machinestrike.game.Orientation;
import machinestrike.game.machine.Machine;
import org.jetbrains.annotations.NotNull;

public class ArmorStrengthRule implements StrengthRule{

    private static ArmorStrengthRule instance;

    public static ArmorStrengthRule instance() {
        if(instance == null) {
            instance = new ArmorStrengthRule();
        }
        return instance;
    }

    private ArmorStrengthRule() {
    }

    @Override
    public int getModifier(@NotNull Machine machine, @NotNull Orientation direction) {
        return machine.armor().inDirection(direction).damageModifier();
    }

}
