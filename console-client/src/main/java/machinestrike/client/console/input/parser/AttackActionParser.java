package machinestrike.client.console.input.parser;

import machinestrike.action.ActionUnion;
import machinestrike.client.console.action.ConsoleActionHandler;
import machinestrike.game.Point;
import machinestrike.game.action.AttackAction;
import machinestrike.action.GameActionHandler;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;

public class AttackActionParser implements Parser<ActionUnion<GameActionHandler, ConsoleActionHandler>> {

    private static AttackActionParser instance;

    public static AttackActionParser instance() {
        if(instance == null) {
            instance = new AttackActionParser();
        }
        return instance;
    }

    private AttackActionParser() {
    }

    @Override
    public @NotNull ActionUnion<GameActionHandler, ConsoleActionHandler> parse(@NotNull Matcher matcher) {
        int oc = matcher.group("oc").toUpperCase().charAt(0) - 'A';
        int or = Integer.parseInt(matcher.group("or")) - 1;
        return ActionUnion.first(new AttackAction(new Point(oc, or)));
    }

}
