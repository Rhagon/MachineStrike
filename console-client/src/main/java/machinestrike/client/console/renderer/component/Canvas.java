package machinestrike.client.console.renderer.component;

import machinestrike.client.console.renderer.shape.Rect;
import machinestrike.debug.Assert;
import machinestrike.game.Point;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class Canvas extends Decorator {

    private char[][] symbols;
    @NotNull
    private Point size;
    private boolean ignoreRepaint;

    public Canvas(int sizeX, int sizeY) {
        this(new Point(sizeX, sizeY));
    }

    public Canvas(@NotNull Point size) {
        this.size = size;
        symbols = new char[size.y()][size.x()];
        ignoreRepaint = false;
    }

    @Contract("_ -> this")
    public Canvas size(@NotNull Point size) {
        this.size = size;
        symbols = new char[size.y()][size.x()];
        fill(' ');
        ignoreRepaint(this::updateLayout);
        repaint();
        return this;
    }

    @Override
    @NotNull
    public Rect rect() {
        return new Rect(Point.ZERO, size);
    }

    public void drawChar(@NotNull Point position, char c) {
        Assert.range(0, position.x(), size.x());
        Assert.range(0, position.y(), size.y());
        symbols[position.y()][position.x()] = c;
    }

    public void fill(char c) {
        fillRect(0, 0, size.x() - 1, size.y() - 1, c);
    }

    public void fillRect(int x, int y, int width, int height, char c) {
        for(int i = 0; i < height; ++i) {
            Arrays.fill(symbols[y + i], x, x + width, c);
        }
    }

    public void fillRect(@NotNull Rect rect, char c) {
        fillRect(rect.x(), rect.y(), rect.width(), rect.height(), c);
    }

    public void printString(@NotNull Point position, @NotNull String string) {
        if(position.y() >= size.y()) {
            return;
        }
        int length = Math.min(size.x() - position.x(), string.length());
        System.arraycopy(string.toCharArray(), 0, symbols[position.y()], position.x(), length);
    }

    @Override
    public void updateLayout() {
        onLayoutChange();
    }

    @Override
    @Contract(pure = true)
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(char[] line : symbols) {
            builder.append(line).append("\n");
        }
        return builder.toString();
    }

    @Override
    public void repaint(@NotNull Rect rect) {
        if(!ignoreRepaint && child() != null) {
            Rect canvasIntersection = rect.intersection(rect());
            fillRect(canvasIntersection, ' ');
            paint(new Graphics(this, canvasIntersection, Point.ZERO));
        }
    }

    public void ignoreRepaint(Runnable run) {
        boolean old = ignoreRepaint;
        ignoreRepaint = true;
        run.run();
        ignoreRepaint = old;
    }

}
