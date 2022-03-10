package machinestrike.client.console.renderer.component;

import machinestrike.client.console.renderer.shape.Rect;
import machinestrike.game.Point;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class Component {

    @Nullable
    private Container parent;
    @NotNull
    private Anchor anchor;
    @NotNull
    private Rect rect;

    public Component() {
        this(Anchor.CENTER);
    }

    public Component(@NotNull Anchor anchor) {
        this.parent = null;
        this.anchor = anchor;
        rect = new Rect();
        updateLayout();
    }

    @NotNull
    @Contract(pure = true)
    public Anchor anchor() {
        return anchor;
    }

    public void anchor(@NotNull Anchor anchor) {
        this.anchor = anchor;
        updateLayout();
    }

    @NotNull
    @Contract(pure = true)
    public Rect rect() {
        return rect;
    }

    @NotNull
    @Contract(pure = true)
    public Point size() {
        return rect().size();
    }

    @NotNull
    @Contract(pure = true)
    public Point position() {
        return rect().position();
    }

    protected abstract void onLayoutChange();

    public void updateLayout() {
        if(parent == null) {
            rect = new Rect(Point.ZERO, anchor.size());
            return;
        }
        Rect parent = this.parent.rect();
        Rect oldRect = rect;
        rect = anchor.applyOn(parent);
        if(!oldRect.equals(rect)) {
            onLayoutChange();
            repaint(oldRect.translate(rect.position().multiply(-1))); //Old rect in new local space
            repaint(new Rect(Point.ZERO, rect.position())); //Rect in local space
        }
    }

    public abstract void paint(Graphics g);

    public void repaint() {
        repaint(new Rect(Point.ZERO, size()));
    }

    public void repaint(int x, int y, int width, int height) {
        repaint(new Rect(x, y, width, height));
    }

    public void repaint(@NotNull Rect rect) {
        if(parent == null) {
            return;
        }
        parent.repaint(rect.translate(position()));
    }

    @Nullable
    @Contract(pure = true)
    public Container parent() {
        return parent;
    }

    protected boolean parent(@Nullable Container parent) {
        if(parent == null || this.parent == null) {
            this.parent = parent;
            return true;
        }
        return false;
    }

}
