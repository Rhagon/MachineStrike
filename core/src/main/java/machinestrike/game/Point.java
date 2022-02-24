package machinestrike.game;

import org.jetbrains.annotations.NotNull;

public record Point(int x, int y) {

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

}
