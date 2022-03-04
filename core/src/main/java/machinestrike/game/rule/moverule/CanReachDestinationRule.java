package machinestrike.game.rule.moverule;

import machinestrike.debug.Assert;
import machinestrike.game.Game;
import machinestrike.game.action.MoveAction;
import machinestrike.game.level.Terrain;
import machinestrike.game.machine.Machine;
import machinestrike.pathfinder.DijkstraPathfinder;
import machinestrike.pathfinder.Pathfinder;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public class CanReachDestinationRule implements MoveRule {

    private static CanReachDestinationRule instance;

    public static CanReachDestinationRule instance() {
        if(instance == null) {
            instance = new CanReachDestinationRule();
        }
        return instance;
    }

    private static final Pathfinder pathfinder = DijkstraPathfinder.instance();

    private CanReachDestinationRule() {
    }

    @Override
    public @NotNull String errorMessage() {
        return "The machine cannot reach the destination";
    }

    @Override
    public boolean test(@NotNull Game game, @NotNull MoveAction action) {
        Machine machine = game.board().field(action.origin()).machine();
        Assert.requireNotNull(machine);
        Predicate<Terrain> canPass = t -> !t.is(Terrain.IMPEDE_MOVEMENT) && ChasmNoGroundedRule.instance().test(t, machine);
        int range = machine.moveRange() + (action.sprint() ? 1 : 0);
        return action.virtualMove() || pathfinder.canReach(game, action.origin(), action.destination(), canPass, range);
    }
}
