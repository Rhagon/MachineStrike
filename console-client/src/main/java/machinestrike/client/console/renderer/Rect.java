package machinestrike.client.console.renderer;

import machinestrike.util.Point;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public record Rect(int x, int y, int width, int height) {

    public Rect(@NotNull Point position, @NotNull Point size) {
        this(position.x(), position.y(), size.x(), size.y());
    }

    public Rect() {
        this(0, 0, 0, 0);
    }

    public Rect(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = Math.max(0, width);
        this.height = Math.max(0, height);
    }

    @NotNull
    @Contract(pure = true)
    public Point size() {
        return new Point(width, height);
    }

    @NotNull
    @Contract(pure = true)
    public Point position() {
        return new Point(x, y);
    }

    @Contract(pure = true)
    public boolean noArea() {
        return width == 0 || height == 0;
    }

    @NotNull
    @Contract(pure = true)
    public Rect intersection(@NotNull Rect second) {
        int startX = Math.max(x, second.x);
        int startY = Math.max(y, second.y);
        int endX = Math.min(x + width, second.x + second.width);
        int endY = Math.min(y + height, second.y + second.height);
        int w = endX - startX;
        int h = endY - startY;
        return new Rect(startX, startY, w, h);
    }

    @NotNull
    @Contract(pure = true)
    public Rect translate(int x, int y) {
        return new Rect(this.x + x, this.y + y, width, height);
    }

    @NotNull
    @Contract(pure = true)
    public Rect translate(@NotNull Point point) {
        return translate(point.x(), point.y());
    }

    @Override
    public boolean equals(Object second) {
        if(second instanceof Rect r) {
            return x == r.x && y == r.y && width == r.width && height == r.height;
        }
        return false;
    }

}
