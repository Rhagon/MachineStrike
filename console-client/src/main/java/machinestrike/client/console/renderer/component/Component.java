package machinestrike.client.console.renderer.component;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

public abstract class Component {

    @Nullable
    private Container parent;

    public Component() {
        parent = null;
    }

    @Nullable
    @Contract(pure = true)
    public Container parent() {
        return parent;
    }

    private int prefWidth, prefHeight;

    @Contract(pure = true)
    public abstract void render(char[][] canvas, int x, int y, int width, int height);

    public abstract void updatePrefSize();

    protected void prefWidth(int prefWidth) {
        this.prefWidth = prefWidth;
    }

    protected void prefHeight(int prefHeight) {
        this.prefHeight = prefHeight;
    }

    @Contract(pure = true)
    public int prefWidth() {
        return prefWidth;
    }

    @Contract(pure = true)
    public int prefHeight() {
        return prefHeight;
    }

    @Contract(pure = true)
    protected void parent(@Nullable Container parent) {
        if(this.parent != null) {
            this.parent.remove(this);
        }
        this.parent = parent;
    }

}
