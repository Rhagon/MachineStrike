package machinestrike.client.console.input;

import machinestrike.util.ActionUnion;
import machinestrike.client.console.action.ConsoleActionHandler;
import machinestrike.client.console.input.parser.Parser;
import machinestrike.game.action.GameActionHandler;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Command(@NotNull Pattern pattern, @NotNull Parser<ActionUnion<GameActionHandler, ConsoleActionHandler>> parser, @NotNull String syntax) {

    public Command(@NotNull String pattern, @NotNull Parser<ActionUnion<GameActionHandler, ConsoleActionHandler>> parser, @NotNull String syntax) {
        this(Pattern.compile(pattern), parser, syntax);
    }

    public boolean matches(@NotNull String input) {
        return pattern.matcher(input).matches();
    }

    public ActionUnion<GameActionHandler, ConsoleActionHandler> parse(@NotNull String input) {
        Matcher matcher = pattern.matcher(input);
        if(matcher.matches()) {
            return parser.parse(matcher);
        }
        return null;
    }

}
