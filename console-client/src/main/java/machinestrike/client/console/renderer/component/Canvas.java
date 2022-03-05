package machinestrike.client.console.renderer.component;

import machinestrike.client.console.renderer.shape.Rect;
import machinestrike.debug.Assert;
import machinestrike.game.Point;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Canvas extends Container {

    private char[][] symbols;

    @Nullable
    private Component stage;

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

    @Nullable
    public Component stage() {
        return stage;
    }

    public boolean stage(@Nullable Component stage) {
        if(this.stage != null) {
            Assert.isTrue(this.stage.parent(null));
        }
        if(stage == null) {
            this.stage = null;
            return true;
        }
        if(stage.parent(this)) {
            this.stage = stage;
            return true;
        }
        return false;
    }

    @NotNull
    @Contract(pure = true)
    @Override
    public Point size() {
        return size;
    }

    @Contract("_ -> this")
    public Canvas size(@NotNull Point size) {
        this.size = size;
        symbols = new char[size.y()][size.x()];
        clear();
        return this;
    }

    public void drawChar(@NotNull Point position, char c) {
        Assert.range(0, position.x(), size.x());
        Assert.range(0, position.y(), size.y());
        symbols[position.y()][position.x()] = c;
    }

    public void clear() {
        fillRect(0, 0, size.x() - 1, size.y() - 1, ' ');
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
    @Contract(pure = true)
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(char[] line : symbols) {
            builder.append(line).append("\n");
        }
        return builder.toString();
    }

    @Override
    @NotNull
    public Rect rect() {
        return new Rect(Point.ZERO, size());
    }

    @Override
    public @NotNull List<Component> children() {
        if(stage == null) {
            return Collections.emptyList();
        }
        return List.of(stage);
    }

    @Override
    public void repaint(@NotNull Rect rect) {
        if(!ignoreRepaint && stage != null) {
            paint(new Graphics(this, rect, Point.ZERO));
        }
    }

    public void ignoreRepaint(boolean ignoreRepaint) {
        this.ignoreRepaint = ignoreRepaint;
    }

    public boolean ignoreRepaint() {
        return ignoreRepaint;
    }

}
