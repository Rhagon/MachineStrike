package machinestrike.client.console;

import machinestrike.client.console.renderer.color.ColorScheme;
import machinestrike.client.console.renderer.color.Color;
import org.jetbrains.annotations.NotNull;

import static machinestrike.client.console.renderer.color.Colors.*;

public class DefaultColorSchemeFactory {

    private static DefaultColorSchemeFactory instance;

    public static DefaultColorSchemeFactory instance() {
        if(instance == null) {
            instance = new DefaultColorSchemeFactory();
        }
        return instance;
    }

    private DefaultColorSchemeFactory() {
    }

    @NotNull
    public ColorScheme create() {
        Color white = new Color(200, 200, 200);
        ColorScheme scheme = new ColorScheme(200, 0, 200);
        scheme.color(CANVAS, white);
        scheme.color(LABEL, white);
        scheme.color(BOX, white);
        scheme.color(PANEL, white);
        scheme.color(BLUE, 0, 102, 255);
        scheme.color(RED, 200, 30, 30);
        scheme.color(CHASM, 102, 51, 0);
        scheme.color(MARSH, 51, 204, 204);
        scheme.color(GRASSLAND, 51, 204, 51);
        scheme.color(FOREST, 0, 102, 0);
        scheme.color(HILL, 115, 115, 115);
        scheme.color(MOUNTAIN, 179, 179, 179);
        scheme.color(ARMORED, 0, 102, 255);
        scheme.color(EXPOSED, 200, 30, 30);
        scheme.color(NORMAL_ARMOR, white);
        return scheme;
    }

}
