package machinestrike.client.console.input;

import machinestrike.game.Orientation;
import machinestrike.game.Player;
import machinestrike.game.Point;
import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

public class Patterns {

    public static final @RegExp String
            NUMBER_PATTERN = "[1-9][0-9]*",
            FIELD_PATTERN = "[A-Za-z]" + NUMBER_PATTERN,
            ORIENTATION_PATTERN = "[nesw]|north|east|south|west",
            PLAYER_PATTERN = "b|r|blue|red";

    @Nullable
    public static Integer parseNumber(@Nullable String str) {
        if(str == null) {
            return null;
        }
        return Integer.parseInt(str);
    }

    @Nullable
    @Contract("null -> null; !null -> !null")
    public static Point parseField(@Nullable String str) {
        if(str == null) {
            return null;
        }
        int column = str.charAt(0) - 'A';
        int row = Integer.parseInt(str.substring(1)) - 1;
        return new Point(column, row);
    }

    @Nullable
    public static Orientation parseOrientation(@Nullable String str) {
        if(str == null) {
            return null;
        }
        return switch(str.charAt(0)) {
            case 'n' -> Orientation.NORTH;
            case 'e' -> Orientation.EAST;
            case 's' -> Orientation.SOUTH;
            case 'w' -> Orientation.WEST;
            default -> null;
        };
    }

    @Nullable
    public static Player parsePlayer(@Nullable String str) {
        if(str == null) {
            return null;
        }
        return str.charAt(0) == 'r' ? Player.RED : Player.BLUE;
    }

}
