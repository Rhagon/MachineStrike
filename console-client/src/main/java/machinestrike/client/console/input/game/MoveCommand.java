package machinestrike.client.console.input.game;

import machinestrike.action.Action;
import machinestrike.client.console.input.Command;
import machinestrike.client.console.input.Patterns;
import machinestrike.debug.Assert;
import machinestrike.game.Orientation;
import machinestrike.game.Point;
import machinestrike.game.action.GameActionHandler;
import machinestrike.game.action.MoveAction;
import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;

public class MoveCommand extends Command<GameActionHandler> {

    private static MoveCommand instance;
    private static final @RegExp String pattern = "(?<mode>move|sprint)\\s(?<origin>" + Patterns.FIELD_PATTERN +
            ")\\sto\\s(?<destination>" + Patterns.FIELD_PATTERN +
            ")\\sfacing\\s(?<orientation>" + Patterns.ORIENTATION_PATTERN + ")";

    public static MoveCommand instance() {
        if(instance == null) {
            instance = new MoveCommand();
        }
        return instance;
    }

    private MoveCommand() {
        super(pattern, "move|sprint <origin> to <destination> facing <orientation>");
    }

    @Override
    protected @Nullable Action<? super GameActionHandler> parse(@NotNull Matcher matcher) {
        boolean sprint = matcher.group("mode").equals("sprint");
        Point origin = Patterns.parseField(matcher.group("origin"));
        Point destination = Patterns.parseField(matcher.group("destination"));
        Orientation orientation = Patterns.parseOrientation(matcher.group("orientation"));
        Assert.requireNotNull(origin);
        Assert.requireNotNull(destination);
        Assert.requireNotNull(orientation);
        return new MoveAction(origin, destination, orientation, sprint, false);
    }
}
