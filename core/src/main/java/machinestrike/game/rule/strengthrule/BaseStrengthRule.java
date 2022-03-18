package machinestrike.game.rule.strengthrule;

import machinestrike.debug.Assert;
import machinestrike.game.Game;
import machinestrike.util.Orientation;
import machinestrike.game.level.Field;
import machinestrike.game.machine.Machine;
import org.jetbrains.annotations.NotNull;

public class BaseStrengthRule implements StrengthRule{

    private static BaseStrengthRule instance;

    public static BaseStrengthRule instance() {
        if(instance == null) {
            instance = new BaseStrengthRule();
        }
        return instance;
    }

    private BaseStrengthRule() {
    }

    @Override
    public int getModifier(@NotNull Machine machine, @NotNull Orientation direction, boolean includeArmor) {
        Field field = machine.field();
        Assert.requireNotNull(field);
        Game game = field.board().game();
        Assert.requireNotNull(game);
        return game.playerOnTurn() == machine.player() ? machine.strength() : 0;
    }

}
