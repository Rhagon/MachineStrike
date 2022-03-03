package machinestrike.game.rule.strengthrule;

import machinestrike.game.level.Field;
import org.jetbrains.annotations.NotNull;

public interface StrengthRule {

    int getModifier(@NotNull Field field);

}
