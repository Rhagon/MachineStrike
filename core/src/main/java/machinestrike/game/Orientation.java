package machinestrike.game;

import org.jetbrains.annotations.NotNull;

public enum Orientation {
    NORTH(0, '\u2191', new Point(0, 1)),
    EAST(1, '\u2192', new Point(1, 0)),
    SOUTH(2, '\u2193', new Point(0, -1)),
    WEST(3, '\u2190', new Point(-1, 0));

    private final int value;
    private final Point point;
    private final char descriptor;

    Orientation(int value, char descriptor, Point point) {
        this.value = value;
        this.point = point;
        this.descriptor = descriptor;
    }

    @NotNull
    private static Orientation fromValue(int value) {
        return switch (value) {
            case 0 -> NORTH;
            case 1 -> EAST;
            case 2 -> SOUTH;
            case 3 -> WEST;
            default -> throw new IllegalArgumentException("Invalid orientation: " + value);
        };
    }

    public char descriptor() {
        return descriptor;
    }

    @NotNull
    public Orientation add(Orientation second) {
        return fromValue((value + second.value) % 4);
    }

    @NotNull
    public Orientation subtract(Orientation second) {
        return fromValue((value - second.value + 4) % 4);
    }

    @NotNull
    public Point asPoint() {
        return point;
    }

}
