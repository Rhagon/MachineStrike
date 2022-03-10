package machinestrike.client.console.input.client;

import machinestrike.action.Action;
import machinestrike.client.console.action.client.ClientActionHandler;
import machinestrike.client.console.action.client.RedrawAction;
import machinestrike.client.console.input.Command;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;

public class RedrawCommand extends Command<ClientActionHandler> {

    private static RedrawCommand instance;

    public static RedrawCommand instance() {
        if(instance == null) {
            instance = new RedrawCommand();
        }
        return instance;
    }

    private RedrawCommand() {
        super("redraw", "redraw", "updates the UI");
    }

    @Override
    protected @Nullable Action<? super ClientActionHandler> parse(@NotNull Matcher matcher) {
        return new RedrawAction();
    }
}
