package machinestrike.client.console.renderer.component;

import org.jetbrains.annotations.Contract;

public class HBox extends AbstractContainer {

    private int separator;

    public HBox() {
        this(0);
    }

    public HBox(int separator) {
        this.separator = separator;
    }

    @Contract("_ -> this")
    public HBox separator(int separator) {
        this.separator = separator;
        updatePrefSize();
        return this;
    }

    @Contract(pure = true)
    public int separator() {
        return separator;
    }

    @Override
    public void render(char[][] canvas, int x, int y, int width, int height) {
        if(height <= 0) {
            return;
        }
        int posX = x;
        for(Component child : children()) {
            int childWidth = Math.min(child.prefWidth(), width - posX);
            if(childWidth > 0) {
                child.render(canvas, x + posX, y, childWidth, height);
                posX += childWidth + separator;
            } else {
                break;
            }
        }
    }

    @Override
    public void updatePrefSize() {
        int opw = prefWidth();
        int oph = prefHeight();
        int npw = 0;
        int nph = 0;
        for(Component child : children()) {
            if(child.prefHeight() > nph) {
                nph = child.prefHeight();
            }
            npw += child.prefWidth();
        }
        npw += (childCount() - 1) * separator;
        prefWidth(npw);
        prefHeight(nph);
        if(opw != npw || oph != nph) {
            if(parent() != null) {
                parent().updatePrefSize();
            }
        }
    }

}
