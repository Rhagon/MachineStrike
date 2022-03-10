package machinestrike.client.console.input.setup;

import machinestrike.client.console.action.setup.PlaceMachineAction;
import machinestrike.client.console.action.setup.SetupActionHandler;
import machinestrike.client.console.input.Command;
import machinestrike.client.console.input.Patterns;
import machinestrike.debug.Assert;
import machinestrike.game.Orientation;
import machinestrike.game.Player;
import machinestrike.game.Point;
import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;

public class PlaceMachineCommand extends Command<SetupActionHandler> {

    private static PlaceMachineCommand instance;
    private static final @RegExp String pattern =
            "place\\s(?<type>[A-Za-z]+)\\s(?:at|on)\\s(?<field>" + Patterns.FIELD_PATTERN
                    + ")(?:\\sfor\\s(?<player>" + Patterns.PLAYER_PATTERN
                    + "))?(?:\\sfacing\\s(?<orientation>" + Patterns.ORIENTATION_PATTERN + "))?";

    public static PlaceMachineCommand instance() {
        if(instance == null) {
            instance = new PlaceMachineCommand();
        }
        return instance;
    }

    private PlaceMachineCommand() {
        super(pattern, "place <type> at|on <field>[ for <player>][ facing <orientation>]");
    }

    @Override
    protected @Nullable PlaceMachineAction parse(@NotNull Matcher matcher) {
        String type = matcher.group("type");
        Point field = Patterns.parseField(matcher.group("field"));
        Player player = Patterns.parsePlayer(matcher.group("player"));
        Orientation orientation = Patterns.parseOrientation(matcher.group("orientation"));
        Assert.requireNotNull(type);
        Assert.requireNotNull(field);
        if(player == null) {
            player = Player.BLUE;
        }
        if(orientation == null) {
            orientation = player == Player.BLUE ? Orientation.NORTH : Orientation.SOUTH;
        }
        return new PlaceMachineAction(type, player, orientation, field);
    }
}
