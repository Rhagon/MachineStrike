package machinestrike.client.console.input;

import machinestrike.game.Orientation;
import machinestrike.game.Point;
import machinestrike.game.action.Action;
import machinestrike.game.action.MoveAction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputHandler implements Iterable<Action> {

    private final BufferedReader reader;

    public InputHandler(InputStream input) {
        reader = new BufferedReader(new InputStreamReader(input));
    }

    @NotNull
    @Override
    public Iterator<Action> iterator() {
        return new Iterator<>() {

            Action buffer = null;

            @Override
            public boolean hasNext() {
                if(buffer == null) {
                    buffer = readAction();
                }
                return buffer != null;
            }

            @Override
            public Action next() {
                if(buffer == null) {
                    buffer = readAction();
                }
                Action a = buffer;
                buffer = null;
                return a;
            }
        };
    }

    private final Pattern movePattern = Pattern.compile("move\\s(?<oc>[A-Za-z])(?<or>[1-9][0-9]*)\\s(?<dc>[A-Za-z])(?<dr>[1-9][0-9]*)\\s(?<dir>[nwse])");

    @Nullable
    private Action readAction() {
        while(true) {
            String line;
            try {
                line = reader.readLine();
            } catch (IOException e) {
                return null;
            }
            if(line.equalsIgnoreCase("exit") || line.equalsIgnoreCase("quit")) {
                return null;
            }
            Matcher matcher = movePattern.matcher(line);
            if(matcher.matches()) {
                return parseMove(matcher);
            }
            System.out.println("Unknown command");
        }
    }

    private MoveAction parseMove(@NotNull Matcher matcher) {
        int oc = matcher.group("oc").toUpperCase().charAt(0) - 'A';
        int or = Integer.parseInt(matcher.group("or")) - 1;
        int dc = matcher.group("dc").toUpperCase().charAt(0) - 'A';
        int dr = Integer.parseInt(matcher.group("dr")) - 1;
        Orientation dir = switch(matcher.group("dir").charAt(0)) {
            case 'n' -> Orientation.NORTH;
            case 'e' -> Orientation.EAST;
            case 's' -> Orientation.SOUTH;
            case 'w' -> Orientation.WEST;
            default -> null;
        };
        if(dir == null) {
            return null;
        }
        return new MoveAction(new Point(oc, or), new Point(dc, dr), dir);
    }

}
