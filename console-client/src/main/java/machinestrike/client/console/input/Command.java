package machinestrike.client.console.input;

import machinestrike.action.Action;
import machinestrike.client.console.input.parser.Parser;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Command<HandlerType>(@NotNull Pattern pattern, @NotNull Parser<? extends Action<? super HandlerType>> parser, @NotNull String syntax) {

    public Command(@NotNull String pattern, @NotNull Parser<? extends Action<? super HandlerType>> parser, @NotNull String syntax) {
        this(Pattern.compile(pattern), parser, syntax);
    }

    public boolean matches(@NotNull String input) {
        return pattern.matcher(input).matches();
    }

    public Action<? super HandlerType> parse(@NotNull String input) {
        Matcher matcher = pattern.matcher(input);
        if(matcher.matches()) {
            return parser.parse(matcher);
        }
        return null;
    }

}
