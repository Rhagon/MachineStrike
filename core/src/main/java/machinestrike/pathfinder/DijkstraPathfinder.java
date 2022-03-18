package machinestrike.pathfinder;

import machinestrike.game.Game;
import machinestrike.util.Point;
import machinestrike.game.level.Field;
import machinestrike.game.level.Terrain;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.function.Predicate;

public class DijkstraPathfinder implements Pathfinder {

    private static DijkstraPathfinder instance;

    public static DijkstraPathfinder instance() {
        if(instance == null) {
            instance = new DijkstraPathfinder();
        }
        return instance;
    }

    private DijkstraPathfinder() {
    }

    @Override
    public @NotNull PathNode[][] calculateDistances(@NotNull Game game, @NotNull Point origin, @NotNull Predicate<Terrain> pathFilter) {
        PathNode[][] graph = new PathNode[game.board().sizeX()][game.board().sizeX()];
        PriorityQueue<PathNode> openList = new PriorityQueue<>(Comparator.comparingInt(PathNode::cost));
        for(Field field : game.board()) {
            PathNode node = new PathNode(field.position(), null, field.position().equals(origin) ? 0 : Integer.MAX_VALUE/2);
            graph[field.position().x()][field.position().y()] = node;
            openList.add(node);
        }
        Predicate<Point> excludeInvalid = p -> !p.inRange(Point.ZERO, new Point(game.board().sizeX() - 1, game.board().sizeY() - 1));
        while(!openList.isEmpty()) {
            PathNode node = openList.poll();
            if(pathFilter.test(game.board().field(node.position()).terrain())|| node.position().equals(origin)) {
                for (Point neighbour : node.position().neighbours(excludeInvalid)) {
                    PathNode neighbourNode = graph[neighbour.x()][neighbour.y()];
                    if (openList.contains(neighbourNode)) {
                        if (node.cost() + 1 < neighbourNode.cost()) {
                            openList.remove(neighbourNode);
                            neighbourNode.updateParent(node, node.cost() + 1);
                            openList.add(neighbourNode);
                        }
                    }
                }
            }
        }
        return graph;
    }
}
