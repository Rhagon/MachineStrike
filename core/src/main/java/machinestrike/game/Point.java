package machinestrike.game;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public record Point(int x, int y) {

    public static final Point ZERO = new Point(0, 0);

    @NotNull
    public Point add(@NotNull Point second) {
        return new Point(x + second.x, y + second.y);
    }

    @NotNull
    public Point subtract(@NotNull Point second) {
        return new Point(x - second.x, y - second.y);
    }

    @NotNull
    public Point multiply(int scalar) {
        return new Point(scalar * x, scalar * y);
    }

    @NotNull
    public Point inDirection(@NotNull Orientation orientation) {
        return add(orientation.asPoint());
    }

    @NotNull
    public Point inDirection(@NotNull Orientation orientation, int steps) {
        return add(orientation.asPoint().multiply(steps));
    }

    /**
     * @return || this - second ||1
     */
    public int sumDistance(@NotNull Point second) {
        return Math.abs(x - second.x) + Math.abs(y - second.y);
    }

    /**
     * @return || this - second ||max
     */
    public int maximumDistance(@NotNull Point second) {
        return Math.max(Math.abs(x - second.x), Math.abs(y - second.y));
    }

    public List<Point> neighbours() {
        return List.of(inDirection(Orientation.NORTH),
                inDirection(Orientation.EAST),
                inDirection(Orientation.SOUTH),
                inDirection(Orientation.WEST));
    }

    public List<Point> neighbours(Predicate<Point> exclude) {
        List<Point> list = new ArrayList<>(neighbours());
        list.removeIf(exclude);
        return list;
    }

    public boolean inRange(@NotNull Point a, @NotNull Point b) {
        return inRange(Math.min(a.x, b.x), x, Math.max(a.x, b.x)) && inRange(Math.min(a.y, b.y), y, Math.max(a.y, b.y));
    }

    private boolean inRange(int min, int value, int max) {
        return min <= value && value <= max;
    }

    @Override
    public boolean equals(Object second) {
        if(second instanceof Point p) {
            return x == p.x && y == p.y;
        }
        return false;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
