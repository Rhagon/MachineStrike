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

    private final Pathfinder pathfinder = DijkstraPathfinder.instance();

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
        return pathfinder.canReach(game, action.origin(), action.destination(), canPass, range);
    }
}
