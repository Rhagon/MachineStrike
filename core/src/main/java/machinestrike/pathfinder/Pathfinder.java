package machinestrike.pathfinder;

import machinestrike.game.Game;
import machinestrike.util.Point;
import machinestrike.game.level.Terrain;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public interface Pathfinder {

    @NotNull PathNode[][] calculateDistances(@NotNull Game game, @NotNull Point origin, @NotNull Predicate<Terrain> pathFilter);

    @Nullable
    default Path search(@NotNull Game game, @NotNull Point origin, @NotNull Point destination, @NotNull Predicate<Terrain> pathFilter, int maxCost) {
        PathNode[][] paths = calculateDistances(game, origin, pathFilter);
        PathNode destinationNode = paths[destination.x()][destination.y()];
        if(destinationNode.cost() <= maxCost) {
            return destinationNode.buildPath();
        }
        return null;
    }

    default boolean canReach(@NotNull Game game, @NotNull Point origin, @NotNull Point destination, @NotNull Predicate<Terrain> pathFilter, int maxCost) {
        return search(game, origin, destination, pathFilter, maxCost) != null;
    }

}
