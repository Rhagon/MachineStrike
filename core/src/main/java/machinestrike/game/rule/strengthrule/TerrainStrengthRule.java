package machinestrike.game.rule.strengthrule;

import machinestrike.debug.Assert;
import machinestrike.game.Orientation;
import machinestrike.game.machine.Machine;
import org.jetbrains.annotations.NotNull;

public class TerrainStrengthRule implements StrengthRule {

    private static TerrainStrengthRule instance;

    public static TerrainStrengthRule instance() {
        if(instance == null) {
            instance = new TerrainStrengthRule();
        }
        return instance;
    }

    private TerrainStrengthRule() {
    }

    @Override
    public int getModifier(@NotNull Machine machine, @NotNull Orientation direction, boolean includeArmor) {
        if(machine.is(Machine.IGNORE_TERRAIN_MODIFIER)) {
            return 0;
        }
        Assert.requireNotNull(machine.field());
        return machine.field().terrain().strengthModifier();
    }

}
