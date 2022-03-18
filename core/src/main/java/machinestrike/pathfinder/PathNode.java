package machinestrike.pathfinder;

import machinestrike.util.Point;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class PathNode {

    @NotNull
    private final Point position;
    @Nullable
    private PathNode parent;
    private int cost;

    public PathNode(@NotNull Point position, @Nullable PathNode parent, int cost) {
        this.position = position;
        this.parent = parent;
        this.cost = cost;
    }

    public void updateParent(PathNode newParent, int newCost) {
        this.parent = newParent;
        this.cost = newCost;
    }

    public Point position() {
        return position;
    }

    public int cost() {
        return cost;
    }

    public Path buildPath() {
        PathNode current = this;
        ArrayList<Point> list = new ArrayList<>();
        int pathCost = 0;
        while(current != null) {
            list.add(current.position);
            pathCost += current.cost;
            current = current.parent;
        }
        return new Path(list, pathCost);
    }

}
