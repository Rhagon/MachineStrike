package machinestrike.client.console.renderer.component;

import machinestrike.client.console.renderer.shape.Rect;
import machinestrike.game.Point;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Graphics {

    @NotNull
    private final Point translation;
    @NotNull
    private final Rect area;
    @NotNull
    private final Canvas device;

    Graphics(@NotNull Canvas device, @NotNull Rect area, @NotNull Point translation) {
        this.area = area;
        this.device = device;
        this.translation = translation;
    }

    @Nullable
    @Contract(pure = true)
    public Graphics create(@NotNull Point translation) {
        return new Graphics(device, area, this.translation.add(translation));
    }

    public void fillRect(@NotNull Rect rect, char symbol) {
        device.fillRect(rect.translate(translation), symbol);
    }

    public void printString(@NotNull Point position, @NotNull String string) {
        device.printString(position.add(translation), string);
    }

    public void printChar(@NotNull Point position, char c) {
        device.drawChar(position.add(translation), c);
    }

    /**
     * @param size the size of the current component that requests the intersection
     */
    @NotNull
    public Rect intersection(@NotNull Point size) {
        return new Rect(Point.ZERO, size).intersection(area.translate(translation.multiply(-1)));
    }

}
