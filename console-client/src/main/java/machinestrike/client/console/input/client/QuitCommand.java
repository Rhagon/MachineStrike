package machinestrike.client.console.input.client;

import machinestrike.action.Action;
import machinestrike.client.console.action.client.ClientActionHandler;
import machinestrike.client.console.action.client.QuitAction;
import machinestrike.client.console.input.Command;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;

public class QuitCommand extends Command<ClientActionHandler> {

    private static QuitCommand instance;

    public static QuitCommand instance() {
        if(instance == null) {
            instance = new QuitCommand();
        }
        return instance;
    }

    private QuitCommand() {
        super("quit|stop|exit", "quit", "exists the game");
    }

    @Override
    protected @Nullable Action<? super ClientActionHandler> parse(@NotNull Matcher matcher) {
        return new QuitAction();
    }
}
