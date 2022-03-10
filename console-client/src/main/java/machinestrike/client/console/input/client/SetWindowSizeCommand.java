package machinestrike.client.console.input.client;

import machinestrike.action.Action;
import machinestrike.client.console.action.client.ClientActionHandler;
import machinestrike.client.console.action.client.SetWindowSizeAction;
import machinestrike.client.console.input.Command;
import machinestrike.client.console.input.Patterns;
import machinestrike.debug.Assert;
import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;

public class SetWindowSizeCommand extends Command<ClientActionHandler> {

    private static SetWindowSizeCommand instance;
    private static final @RegExp String pattern =
            "(?:set|change)\\swindow\\ssize\\sto\\s(?<width>" + Patterns.NUMBER_PATTERN + ")\\s(?<height>" + Patterns.NUMBER_PATTERN + ")";

    public static SetWindowSizeCommand instance() {
        if(instance == null) {
            instance = new SetWindowSizeCommand();
        }
        return instance;
    }

    private SetWindowSizeCommand() {
        super(pattern, "set window size to <width> <height>");
    }

    @Override
    protected @Nullable Action<? super ClientActionHandler> parse(@NotNull Matcher matcher) {
        Integer width = Patterns.parseNumber(matcher.group("width"));
        Integer height = Patterns.parseNumber(matcher.group("height"));
        Assert.requireNotNull(width);
        Assert.requireNotNull(height);
        return new SetWindowSizeAction(width, height);
    }

}
