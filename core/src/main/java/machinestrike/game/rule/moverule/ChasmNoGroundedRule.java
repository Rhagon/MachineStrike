package machinestrike.game.rule.moverule;

import machinestrike.debug.Assert;
import machinestrike.game.Game;
import machinestrike.game.action.MoveAction;
import machinestrike.game.level.Terrain;
import machinestrike.game.machine.Machine;
import org.jetbrains.annotations.NotNull;

public class ChasmNoGroundedRule implements MoveRule{

    private static ChasmNoGroundedRule instance;

    public static ChasmNoGroundedRule instance() {
        if(instance == null) {
            instance = new ChasmNoGroundedRule();
        }
        return instance;
    }

    private ChasmNoGroundedRule() {
    }

    @Override
    public @NotNull String errorMessage() {
        return "A grounded machine cannot step on a chasm";
    }

    public boolean test(@NotNull Terrain terrain, @NotNull Machine machine) {
        return !machine.is(Machine.GROUNDED) || !terrain.is(Terrain.EXCLUDE_GROUNDED);
    }

    @Override
    public boolean test(@NotNull Game game, @NotNull MoveAction<?> action) {
        Machine machine = game.board().field(action.origin()).machine();
        Terrain terrain = game.board().field(action.destination()).terrain();
        Assert.requireNotNull(machine);
        return test(terrain, machine);
    }

}
