package machinestrike.client.console.input.parser;

import machinestrike.client.console.action.ClientActionHandler;
import machinestrike.game.Point;
import machinestrike.game.action.AttackAction;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;

public class AttackActionParser implements Parser<AttackAction<ClientActionHandler>> {

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
    public @NotNull AttackAction<ClientActionHandler> parse(@NotNull Matcher matcher) {
        int oc = matcher.group("oc").toUpperCase().charAt(0) - 'A';
        int or = Integer.parseInt(matcher.group("or")) - 1;
        return new AttackAction<>(new Point(oc, or));
    }

}
