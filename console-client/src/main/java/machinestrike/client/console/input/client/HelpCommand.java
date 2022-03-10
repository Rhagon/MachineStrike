package machinestrike.client.console.input.client;

import machinestrike.action.Action;
import machinestrike.client.console.action.client.ClientActionHandler;
import machinestrike.client.console.action.client.HelpAction;
import machinestrike.client.console.input.Command;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;

public class HelpCommand extends Command<ClientActionHandler> {

    private static HelpCommand instance;

    public static HelpCommand instance() {
        if(instance == null) {
            instance = new HelpCommand();
        }
        return instance;
    }

    private HelpCommand() {
        super("help", "help", "prints a list of all available commands");
    }

    @Override
    protected @Nullable Action<? super ClientActionHandler> parse(@NotNull Matcher matcher) {
        return new HelpAction();
    }
}
