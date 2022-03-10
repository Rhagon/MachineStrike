package machinestrike.client.console.input.setup;

import machinestrike.client.console.action.setup.ClientSetupActionHandler;
import machinestrike.client.console.action.setup.StartGameAction;
import machinestrike.client.console.input.Command;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;

public class StartGameCommand extends Command<ClientSetupActionHandler> {

    private static StartGameCommand instance;

    public static StartGameCommand instance() {
        if(instance == null) {
            instance = new StartGameCommand();
        }
        return instance;
    }

    private StartGameCommand() {
        super("start game", "start game");
    }

    @Override
    protected @Nullable StartGameAction parse(@NotNull Matcher matcher) {
        return new StartGameAction();
    }
}
