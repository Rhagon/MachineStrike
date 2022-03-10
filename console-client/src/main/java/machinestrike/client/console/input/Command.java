package machinestrike.client.console.input;

import machinestrike.action.Action;
import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Command<HandlerType> {

    @NotNull
    private final Pattern pattern;
    @NotNull
    private final String syntax, description;

    public Command(@NotNull @RegExp String pattern) {
        this(pattern, pattern);
    }

    public Command(@NotNull @RegExp String pattern, @NotNull String syntax) {
        this(pattern, syntax, "");
    }

    public Command(@NotNull @RegExp String pattern,  @NotNull String syntax, @NotNull String description) {
        this.pattern = Pattern.compile(pattern);
        this.syntax = syntax;
        this.description = description;
    }

    @NotNull
    public Pattern pattern() {
        return pattern;
    }

    @NotNull
    public String syntax() {
        return syntax;
    }

    @NotNull
    public String description() {
        return description;
    }

    public boolean matches(@NotNull String input) {
        return pattern.matcher(input).matches();
    }

    @Nullable
    public Action<? super HandlerType> parse(@NotNull String input) {
        Matcher matcher = pattern.matcher(input);
        if(matcher.matches()) {
            return parse(matcher);
        }
        return null;
    }

    @Nullable
    protected abstract Action<? super HandlerType> parse(@NotNull Matcher matcher);

}
