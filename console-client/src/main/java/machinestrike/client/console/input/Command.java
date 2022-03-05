package machinestrike.client.console.input;

import machinestrike.action.Action;
import machinestrike.client.console.action.ClientActionHandler;
import machinestrike.client.console.input.parser.Parser;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Command<ActionType extends Action<? super ClientActionHandler>>(@NotNull Pattern pattern, @NotNull Parser<ActionType> parser, @NotNull String syntax) {

    public Command(@NotNull String pattern, @NotNull Parser<ActionType> parser, @NotNull String syntax) {
        this(Pattern.compile(pattern), parser, syntax);
    }

    public boolean matches(@NotNull String input) {
        return pattern.matcher(input).matches();
    }

    public ActionType parse(@NotNull String input) {
        Matcher matcher = pattern.matcher(input);
        if(matcher.matches()) {
            return parser.parse(matcher);
        }
        return null;
    }

}
