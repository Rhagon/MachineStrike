package machinestrike.client.console.input.parser;

import machinestrike.action.ActionUnion;
import machinestrike.client.console.action.ConsoleActionHandler;
import machinestrike.action.GameActionHandler;
import machinestrike.game.action.MoveAction;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;

public class SprintActionParser implements Parser<ActionUnion<GameActionHandler, ConsoleActionHandler>> {

    private static SprintActionParser instance;

    public static SprintActionParser instance() {
        if(instance == null) {
            instance = new SprintActionParser();
        }
        return instance;
    }

    private SprintActionParser() {
    }

    @Override
    @NotNull
    public ActionUnion<GameActionHandler, ConsoleActionHandler> parse(@NotNull Matcher matcher) {
        MoveAction move = MoveActionParser.instance().parseToAction(matcher);
        return ActionUnion.first(new MoveAction(move.origin(), move.destination(), move.orientation(), true));
    }
}
