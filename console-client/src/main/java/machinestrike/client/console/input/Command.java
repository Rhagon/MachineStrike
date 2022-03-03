package machinestrike.client.console.input;

import machinestrike.client.console.input.parser.ActionParser;
import machinestrike.game.action.Action;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Command(@NotNull Pattern pattern, @NotNull ActionParser<?> parser, @NotNull String syntax) {

    public Command(@NotNull String pattern, @NotNull ActionParser<?> parser, @NotNull String syntax) {
        this(Pattern.compile(pattern), parser, syntax);
    }

    public boolean matches(@NotNull String input) {
        return pattern.matcher(input).matches();
    }

    public Action parse(@NotNull String input) {
        Matcher matcher = pattern.matcher(input);
        if(matcher.matches()) {
            return parser.parse(matcher);
        }
        return null;
    }

}
