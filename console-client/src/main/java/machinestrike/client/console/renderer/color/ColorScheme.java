package machinestrike.client.console.renderer.color;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class ColorScheme {

    @NotNull
    private final HashMap<ColorKey, Color> colors;
    @NotNull
    private Color defaultColor;

    public ColorScheme(int r, int g, int b) {
        this(new Color(r, g, b));
    }

    public ColorScheme(@NotNull Color defaultColor) {
        this.defaultColor = defaultColor;
        colors = new HashMap<>();
    }

    @NotNull
    public Color defaultColor() {
        return defaultColor;
    }

    public void defaultColor(@NotNull Color defaultColor) {
        this.defaultColor = defaultColor;
    }

    public void color(@NotNull ColorKey key, Color color) {
        colors.put(key, color);
    }

    public void color(@NotNull ColorKey key, int r, int g, int b) {
        color(key, new Color(r, g, b));
    }

    @NotNull
    public Color color(@NotNull ColorKey key) {
        Color color = colors.get(key);
        if(color == null) {
            return defaultColor;
        }
        return color;
    }

}
