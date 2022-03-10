package machinestrike.pathfinder;

import machinestrike.game.Point;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Path implements Iterable<Point> {

    private final List<Point> steps;
    private final int cost;

    public Path(List<Point> steps, int totalCost) {
        this.steps = Collections.unmodifiableList(steps);
        this.cost = totalCost;
    }

    public List<Point> steps() {
        return steps;
    }

    public int totalCost() {
        return cost;
    }

    @Override
    public Iterator<Point> iterator() {
        return steps.iterator();
    }

}
