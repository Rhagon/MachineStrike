package machinestrike.client.console.input.parser;

import machinestrike.action.ActionUnion;
import machinestrike.client.console.action.ConsoleActionHandler;
import machinestrike.game.Orientation;
import machinestrike.game.Point;
import machinestrike.action.GameActionHandler;
import machinestrike.game.action.MoveAction;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;

public class MoveActionParser implements Parser<ActionUnion<GameActionHandler, ConsoleActionHandler>> {

    private static MoveActionParser instance;

    public static MoveActionParser instance() {
        if(instance == null) {
            instance = new MoveActionParser();
        }
        return instance;
    }

    private MoveActionParser() {
    }

    @Override
    @NotNull
    public ActionUnion<GameActionHandler, ConsoleActionHandler> parse(@NotNull Matcher matcher) {
        return ActionUnion.first(parseToAction(matcher));
    }

    @NotNull
    public MoveAction parseToAction(@NotNull Matcher matcher) {
        int oc = matcher.group("oc").toUpperCase().charAt(0) - 'A';
        int or = Integer.parseInt(matcher.group("or")) - 1;
        int dc = matcher.group("dc").toUpperCase().charAt(0) - 'A';
        int dr = Integer.parseInt(matcher.group("dr")) - 1;
        Orientation dir = switch(matcher.group("dir").charAt(0)) {
            case 'n' -> Orientation.NORTH;
            case 'e' -> Orientation.EAST;
            case 's' -> Orientation.SOUTH;
            case 'w' -> Orientation.WEST;
            default -> throw new IllegalStateException("invalid direction that should have been captured by the caller");
        };
        return new MoveAction(new Point(oc, or), new Point(dc, dr), dir, false);
    }
}
