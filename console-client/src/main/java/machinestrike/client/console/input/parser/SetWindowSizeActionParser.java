package machinestrike.client.console.input.parser;

import machinestrike.client.console.action.SetWindowSizeAction;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;

public class SetWindowSizeActionParser implements Parser<SetWindowSizeAction> {

    private static SetWindowSizeActionParser instance;

    public static SetWindowSizeActionParser instance() {
        if(instance == null) {
            instance = new SetWindowSizeActionParser();
        }
        return instance;
    }

    @Override
    public @NotNull SetWindowSizeAction parse(@NotNull Matcher matcher) {
        int width = Integer.parseInt(matcher.group("w"));
        int height = Integer.parseInt(matcher.group("h"));
        return new SetWindowSizeAction(width, height);
    }
}
