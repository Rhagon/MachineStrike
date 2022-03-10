package machinestrike.client.console.input.parser;

import machinestrike.client.console.action.setup.PlaceMachineAction;
import machinestrike.game.Orientation;
import machinestrike.game.Player;
import machinestrike.game.Point;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;

public class PlaceMachineActionParser implements Parser<PlaceMachineAction> {

    private static PlaceMachineActionParser instance;

    public static PlaceMachineActionParser instance() {
        if(instance == null) {
            instance = new PlaceMachineActionParser();
        }
        return instance;
    }

    private PlaceMachineActionParser() {
    }

    @Override
    public @NotNull PlaceMachineAction parse(@NotNull Matcher matcher) {
        String name = matcher.group("name");
        if(name.equalsIgnoreCase("none")) {
            name = null;
        }
        int column = matcher.group("column").toUpperCase().charAt(0) - 'A';
        int row = Integer.parseInt(matcher.group("row")) - 1;
        Player player = Player.BLUE;
        String playerString = matcher.group("player");
        if(playerString != null) {
            player = switch (playerString.charAt(0)) {
                case 'b' -> Player.BLUE;
                case 'r' -> Player.RED;
                default -> throw new IllegalStateException("Invalid player name that should habe been captured by the caller");
            };
        }
        Orientation orientation = Orientation.NORTH;
        String orientationString = matcher.group("orientation");
        if(orientationString != null) {
            orientation = switch(orientationString.charAt(0)) {
                case 'n' -> Orientation.NORTH;
                case 'e' -> Orientation.EAST;
                case 's' -> Orientation.SOUTH;
                case 'w' -> Orientation.WEST;
                default -> throw new IllegalStateException("Invalid orientation that should have been captured by the caller");
            };
        }
        return new PlaceMachineAction(name, player, orientation, new Point(column, row));
    }

}
