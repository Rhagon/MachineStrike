package machinestrike.client.console.renderer;

import machinestrike.client.console.renderer.component.Component;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class CanvasRenderer {

    int sizeX, sizeY;
    char[][] canvas;
    char defaultSymbol;

    public CanvasRenderer(int sizeX, int sizeY, char defaultSymbol) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        canvas = new char[sizeY][sizeX];
        this.defaultSymbol = defaultSymbol;
        clear();
    }

    public void clear() {
        for(char[] line : canvas) {
            Arrays.fill(line, defaultSymbol);
        }
    }

    String render(@NotNull Component component) {
        clear();
        component.render(canvas, 0, 0, sizeX, sizeY);
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < sizeY; ++i) {
            builder.append(canvas[i]).append('\n');
        }
        return builder.toString();
    }

}
