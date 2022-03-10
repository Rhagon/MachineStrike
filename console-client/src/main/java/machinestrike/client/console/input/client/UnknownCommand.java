package machinestrike.client.console.input.client;

import machinestrike.action.Action;
import machinestrike.client.console.action.client.ClientActionHandler;
import machinestrike.client.console.action.client.UnknownCommandAction;
import machinestrike.client.console.input.Command;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;

public class UnknownCommand extends Command<ClientActionHandler> {

    private static UnknownCommand instance;

    public static UnknownCommand instance() {
        if(instance == null) {
            instance = new UnknownCommand();
        }
        return instance;
    }

    private UnknownCommand() {
        super(".*", "", "");
    }

    @Override
    protected @Nullable Action<? super ClientActionHandler> parse(@NotNull Matcher matcher) {
        return new UnknownCommandAction(matcher.group());
    }
}
