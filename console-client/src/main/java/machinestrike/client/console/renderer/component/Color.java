package machinestrike.client.console.renderer.component;

import org.jetbrains.annotations.NotNull;

public enum Color {

    WHITE(200, 200, 200),
    CHASM(102, 51, 0),
    MARSH(51, 204, 204),
    GRASSLAND(51, 204, 51),
    FOREST(0, 102, 0),
    HILL(115, 115, 115),
    MOUNTAIN(179, 179, 179)
    ;

    public static Color forName(@NotNull String name) {
        return switch(name.toLowerCase()) {
            case "chasm" -> CHASM;
            case "marsh" -> MARSH;
            case "grassland" -> GRASSLAND;
            case "forest" -> FOREST;
            case "hill" -> HILL;
            case "mountain" -> MOUNTAIN;
            default -> WHITE;
        };
    }

    private final String str;

    Color(int r, int g, int b) {
        this.str = "\033[38;2;" + r + ";" + g + ";" + b + "m";
    }

    @Override
    public String toString() {
        return str;
    }

}
