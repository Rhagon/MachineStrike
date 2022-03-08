package machinestrike.client.console.renderer.component;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BoxDecorator extends Component implements Container {

    @Nullable
    private Component child;
    private int hPad, vPad;

    public BoxDecorator() {
        this(null);
    }

    public BoxDecorator(@Nullable Component child) {
        this(child, 0, 0);
    }

    public BoxDecorator(@Nullable Component child, int hPad, int vPad) {
        this.child = child;
        this.hPad = hPad;
        this.vPad = vPad;
    }

    @Contract(pure = true)
    @Nullable
    public Component child() {
        return child;
    }

    @Contract("_ -> this")
    public BoxDecorator child(Component child) {
        if(this.child != null) {
            remove(this.child);
        }
        this.child = child;
        child.parent(this);
        return this;
    }

    @Contract(pure = true)
    public int hPad() {
        return hPad;
    }

    @Contract("_ -> this")
    public BoxDecorator hPad(int hPad) {
        this.hPad = hPad;
        updatePrefSize();
        return this;
    }

    @Contract(pure = true)
    public int vPad() {
        return vPad;
    }

    @Contract("_ -> this")
    public BoxDecorator vPad(int vPad) {
        this.vPad = vPad;
        updatePrefSize();
        return this;
    }

    @Contract("_ -> this")
    @Override
    public Container remove(@NotNull Component component) {
        if(child == null) {
            return this;
        }
        if(child == component) {
            this.child = null;
            component.parent(null);
        }
        return this;
    }

    @Override
    public void render(char[][] canvas, int x, int y, int width, int height) {
        if(height < 2 || width < 2) {
            return;
        }
        canvas[y][x] = '+';
        Arrays.fill(canvas[y], x + 1, x + width - 1, '-');
        canvas[y][x + width - 1] = '+';
        canvas[y + height - 1][x] = '+';
        Arrays.fill(canvas[y + height - 1], x + 1, x + width - 1, '-');
        canvas[y + height - 1][x + width - 1] = '+';
        for(int i = 1; i < height - 1; ++i) {
            canvas[y + i][x] = '|';
            canvas[y + i][x + width - 1] = '|';
        }
        if(child != null) {
            child.render(canvas, x + 1 + hPad, y + 1 + vPad, width - 2 - 2 * hPad, height - 2 - 2 * vPad);
        }
    }

    @Override
    public List<Component> children() {
        if(child == null) {
            return Collections.emptyList();
        }
        return List.of(child);
    }

    @Override
    public int childCount() {
        return child == null ? 0 : 1;
    }

    @Override
    public void updatePrefSize() {
        int h = child == null ? 0 : child.prefHeight();
        int w = child == null ? 0 : child.prefWidth();
        prefHeight(h + 2 + 2 * vPad);
        prefWidth(w + 2 + 2 * hPad);
    }

}
