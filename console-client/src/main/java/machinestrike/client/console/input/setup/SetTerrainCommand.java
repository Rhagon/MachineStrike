package machinestrike.client.console.input.setup;

import machinestrike.client.console.action.setup.SetTerrainAction;
import machinestrike.client.console.action.setup.SetupActionHandler;
import machinestrike.client.console.input.Command;
import machinestrike.client.console.input.Patterns;
import machinestrike.debug.Assert;
import machinestrike.game.Point;
import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;

public class SetTerrainCommand extends Command<SetupActionHandler> {

    private static SetTerrainCommand instance;
    private static final @RegExp String pattern = "(?:set|change)\\sterrain\\sat\\s(?<field>" +
            Patterns.FIELD_PATTERN + ")\\sto\\s(?<type>[A-Za-z]+)";

    public static SetTerrainCommand instance() {
        if(instance == null) {
            instance = new SetTerrainCommand();
        }
        return instance;
    }

    private SetTerrainCommand() {
        super(pattern, "set terrain at <field> to <type>");
    }

    @Override
    protected @Nullable SetTerrainAction parse(@NotNull Matcher matcher) {
        Point field = Patterns.parseField(matcher.group("field"));
        String type = matcher.group("type");
        Assert.requireNotNull(field);
        Assert.requireNotNull(type);
        return new SetTerrainAction(field, type);
    }
}
