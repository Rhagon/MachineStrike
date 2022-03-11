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
    private Color[][] colors;

    @NotNull
    private Point size;
    private boolean ignoreRepaint;

    public Canvas(int sizeX, int sizeY) {
        this(new Point(sizeX, sizeY));
    }

    public Canvas(@NotNull Point size) {
        this.size = size;
        symbols = new char[size.y()][size.x()];
        colors = new Color[size.y()][size.x()];
        ignoreRepaint = false;
        fill(' ', Color.WHITE);
    }

    @Contract("_ -> this")
    public Canvas size(@NotNull Point size) {
        this.size = size;
        symbols = new char[size.y()][size.x()];
        colors = new Color[size.y()][size.x()];
        fill(' ', Color.WHITE);
        ignoreRepaint(this::updateLayout);
        repaint();
        return this;
    }

    @Override
    @NotNull
    public Rect rect() {
        return new Rect(Point.ZERO, size);
    }

    public void drawChar(@NotNull Point position, char symbol, @NotNull Color color) {
        Assert.range(0, position.x(), size.x());
        Assert.range(0, position.y(), size.y());
        symbols[position.y()][position.x()] = symbol;
        colors[position.y()][position().x()] = color;
    }

    public void fill(char symbol, @NotNull Color color) {
        fillRect(0, 0, size.x() - 1, size.y() - 1, symbol, color);
    }

    public void fillRect(int x, int y, int width, int height, char symbol, @NotNull Color color) {
        for(int i = 0; i < height; ++i) {
            Arrays.fill(symbols[y + i], x, x + width, symbol);
            Arrays.fill(colors[y + i], x, x + width, color);
        }
    }

    public void fillRect(@NotNull Rect rect, char symbol, @NotNull Color color) {
        fillRect(rect.x(), rect.y(), rect.width(), rect.height(), symbol, color);
    }

    public void printString(@NotNull Point position, @NotNull String string, @NotNull Color color) {
        if(position.y() >= size.y()) {
            return;
        }
        int length = Math.min(size.x() - position.x(), string.length());
        System.arraycopy(string.toCharArray(), 0, symbols[position.y()], position.x(), length);
        Arrays.fill(colors[position.y()], position.x(), position.x() + string.length(), color);
    }

    @Override
    public void updateLayout() {
        onLayoutChange();
    }

    @Override
    @Contract(pure = true)
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(int r = 0; r < size.y(); ++r) {
            Color current = null;
            for(int c = 0; c < size.x(); ++c) {
                if(colors[r][c] != current) {
                    current = colors[r][c];
                    builder.append(current);
                }
                builder.append(symbols[r][c]);
            }
            builder.append("\n");
        }
        builder.append("\033[39m");
        return builder.toString();
    }

    @Override
    public void repaint(@NotNull Rect rect) {
        if(!ignoreRepaint && child() != null) {
            Rect canvasIntersection = rect.intersection(rect());
            fillRect(canvasIntersection, ' ', Color.WHITE);
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
