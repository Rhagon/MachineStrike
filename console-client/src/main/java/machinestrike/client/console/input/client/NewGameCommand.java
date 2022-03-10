package machinestrike.client.console.input.client;

import machinestrike.action.Action;
import machinestrike.client.console.action.client.ClientActionHandler;
import machinestrike.client.console.action.client.NewGameAction;
import machinestrike.client.console.input.Command;
import machinestrike.client.console.input.Patterns;
import machinestrike.game.Point;
import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;

public class NewGameCommand extends Command<ClientActionHandler> {

    private static NewGameCommand instance;
    private static final @RegExp String pattern =
        "new\\sgame(?:\\swith\\ssize\\s(?<width>" + Patterns.NUMBER_PATTERN + ")\\s(?<height>" + Patterns.NUMBER_PATTERN + "))?";

    public static NewGameCommand instance() {
        if(instance == null) {
            instance = new NewGameCommand();
        }
        return instance;
    }

    private NewGameCommand() {
        super(pattern, "new game[ with size <width> <height>]", "creates a new board and enters edit mode");
    }

    @Override
    protected @Nullable Action<? super ClientActionHandler> parse(@NotNull Matcher matcher) {
        Integer width = Patterns.parseNumber(matcher.group("width"));
        Integer height = Patterns.parseNumber(matcher.group("height"));
        Point size = width == null || height == null ? new Point(8, 8) : new Point(width, height);
        return new NewGameAction(size);
    }
}
