package machinestrike.client.console.renderer.component;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BorderBox extends Component implements Container {

    public enum ExpansionPolicy {
        CENTER,
        BORDER
    }

    public enum Slot {
        TOP(0),
        LEFT(1),
        CENTER(2),
        RIGHT(3),
        BOTTOM(4);

        private final int index;

        Slot(int index) {
            this.index = index;
        }

    }

    @Nullable
    private final Component[] children;
    @NotNull
    private ExpansionPolicy policy;

    public BorderBox(@NotNull ExpansionPolicy policy) {
        children = new Component[5];
        this.policy = policy;
    }

    @Nullable
    @Contract(pure = true)
    public Component child(Slot slot) {
        return children[slot.index];
    }

    @Contract("_, _ -> this")
    public BorderBox child(Slot slot, Component child) {
        children[slot.index] = child;
        return this;
    }

    @NotNull
    public ExpansionPolicy policy() {
        return policy;
    }

    @Contract("_ -> this")
    public BorderBox policy(@NotNull ExpansionPolicy policy) {
        this.policy = policy;
        return this;
    }

    @Override
    public void render(char[][] canvas, int x, int y, int width, int height) {
        if(width <= 0 || height <= 0) {
            return;
        }
        int th, ch, bh;
        if(policy == ExpansionPolicy.CENTER) {
            ch = Math.min(height, prefHeight(Slot.CENTER));
            th = Math.min(height - ch, prefHeight(Slot.TOP));
            bh = Math.min(height - ch - th, prefHeight(Slot.BOTTOM));
        } else {
            th = Math.min(height, prefHeight(Slot.TOP));
            bh = Math.min(height - th, prefHeight(Slot.BOTTOM));
            ch = Math.min(height - th - bh, prefHeight(Slot.CENTER));
        }
        int lw, cw, rw;
        if(policy == ExpansionPolicy.CENTER) {
            cw = Math.min(width, prefWidth(Slot.CENTER));
            lw = Math.min(width - cw, prefWidth(Slot.LEFT));
            rw = Math.min(width - cw - lw, prefWidth(Slot.RIGHT));
        } else {
            lw = Math.min(width, prefWidth(Slot.LEFT));
            rw = Math.min(width - lw, prefWidth(Slot.RIGHT));
            cw = Math.min(width - lw - rw, prefWidth(Slot.CENTER));
        }
        render(Slot.TOP, canvas, x, y, width, th);
        render(Slot.LEFT, canvas, x, y + th, lw, ch);
        render(Slot.CENTER, canvas, x + lw, y + th, cw, ch);
        render(Slot.RIGHT, canvas, x + lw + cw, y + th, lw, ch);
        render(Slot.BOTTOM, canvas, x, y + th + ch, width, bh);
    }

    @Override
    public List<Component> children() {
        return List.of(children);
    }

    @Override
    public int childCount() {
        return children().size();
    }

    @Override
    public void updatePrefSize() {
        prefWidth(Math.max(Math.max(prefWidth(Slot.TOP), prefWidth(Slot.BOTTOM)),
                prefWidth(Slot.LEFT) + prefWidth(Slot.CENTER) + prefWidth(Slot.RIGHT)));
        prefHeight(prefHeight(Slot.TOP) + prefHeight(Slot.BOTTOM) +
                Math.max(Math.max(prefHeight(Slot.LEFT), prefHeight(Slot.CENTER)), prefHeight(Slot.RIGHT)));
    }

    private int prefWidth(@NotNull Slot slot) {
        Component child = child(slot);
        return child == null ? 0 : child.prefWidth();
    }

    private int prefHeight(@NotNull Slot slot) {
        Component child = child(slot);
        return child == null ? 0 : child.prefHeight();
    }

    private void render(@NotNull Slot slot, char[][] canvas, int x, int y, int width, int height) {
        if(width <= 0 || height <= 0) {
            return;
        }
        Component child = child(slot);
        if(child != null) {
            child.render(canvas, x, y, width, height);
        }
    }

    @Override
    @Contract("_ -> this")
    public BorderBox remove(@NotNull Component child) {
        for(int i = 0; i < children.length; ++i) {
            if(children[i] == child) {
                children[i] = null;
            }
        }
        child.parent(null);
        return this;
    }
}
