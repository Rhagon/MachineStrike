package machinestrike.client.console.input.parser;

import machinestrike.client.console.action.ClientActionHandler;
import machinestrike.game.action.MoveAction;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;

public class SprintActionParser implements Parser<MoveAction<ClientActionHandler>> {

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
    public MoveAction<ClientActionHandler> parse(@NotNull Matcher matcher) {
        MoveAction<ClientActionHandler> move = MoveActionParser.instance().parse(matcher);
        return new MoveAction<>(move.origin(), move.destination(), move.orientation(), true);
    }
}
