package machinestrike.game.rule.strengthrule;

import machinestrike.game.Orientation;
import machinestrike.game.machine.Machine;
import org.jetbrains.annotations.NotNull;

public interface StrengthRule {

    int getModifier(@NotNull Machine machine, @NotNull Orientation direction, boolean includeArmor);

}
