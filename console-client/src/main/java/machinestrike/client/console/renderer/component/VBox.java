package machinestrike.client.console.renderer.component;

import org.jetbrains.annotations.Contract;

public class VBox extends AbstractContainer {

    private int separator;

    public VBox() {
        this(0);
    }

    public VBox(int separator) {
        this.separator = separator;
    }

    @Contract("_ -> this")
    public VBox separator(int separator) {
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
        if(width <= 0) {
            return;
        }
        int posY = y;
        for(Component child : children()) {
            int childHeight = Math.min(child.prefHeight(), height - posY);
            if(childHeight > 0) {
                child.render(canvas, x, y + childHeight, width, childHeight);
                posY += childHeight + separator;
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
            if(child.prefWidth() > npw) {
                npw = child.prefHeight();
            }
            nph += child.prefHeight();
        }
        nph += (childCount() - 1) * separator;
        prefWidth(npw);
        prefHeight(nph);
        if(opw != npw || oph != nph) {
            if(parent() != null) {
                parent().updatePrefSize();
            }
        }
    }

}
