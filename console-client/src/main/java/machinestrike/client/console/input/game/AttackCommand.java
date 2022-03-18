package machinestrike.client.console.input.game;

import machinestrike.action.Action;
import machinestrike.client.console.input.Command;
import machinestrike.client.console.input.Patterns;
import machinestrike.debug.Assert;
import machinestrike.util.Point;
import machinestrike.game.action.AttackAction;
import machinestrike.game.action.GameActionHandler;
import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;

public class AttackCommand extends Command<GameActionHandler> {

    private static AttackCommand instance;
    private static final @RegExp String pattern = "attack\\swith\\s(?<field>" + Patterns.FIELD_PATTERN + ")";

    public static AttackCommand instance() {
        if(instance == null) {
            instance = new AttackCommand();
        }
        return instance;
    }

    private AttackCommand() {
        super(pattern, "attack with <field>");
    }

    @Override
    protected @Nullable Action<? super GameActionHandler> parse(@NotNull Matcher matcher) {
        Point field = Patterns.parseField(matcher.group("field"));
        Assert.requireNotNull(field);
        return new AttackAction(field);
    }
}
