package machinestrike.client.console.input.game;

import machinestrike.action.Action;
import machinestrike.client.console.action.game.ClientGameActionHandler;
import machinestrike.client.console.input.Command;
import machinestrike.client.console.input.CommandListFactory;
import machinestrike.client.console.input.client.*;
import machinestrike.game.action.EndTurnAction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;
import java.util.regex.Matcher;

public class ClientGameCommandFactory implements CommandListFactory<ClientGameActionHandler> {

    private static ClientGameCommandFactory instance;

    public static ClientGameCommandFactory instance() {
        if(instance == null) {
            instance = new ClientGameCommandFactory();
        }
        return instance;
    }

    private ClientGameCommandFactory() {
    }

    @NotNull
    @Unmodifiable
    public List<Command<? super ClientGameActionHandler>> create() {
        return List.of(
                AttackCommand.instance(),
                HelpCommand.instance(),
                MoveCommand.instance(),
                command("end turn", EndTurnAction::new),
                NewGameCommand.instance(),
                QuitCommand.instance(),
                RedrawCommand.instance(),
                SetWindowSizeCommand.instance(),
                UnknownCommand.instance()
        );
    }

    private interface Parser {
        @Nullable
        Action<? super ClientGameActionHandler> parse(@NotNull Matcher matcher);
    }

    private static Command<ClientGameActionHandler> command(@NotNull String pattern, Parser parser) {
        return new Command<>(pattern) {
            @Override
            protected @Nullable Action<? super ClientGameActionHandler> parse(@NotNull Matcher matcher) {
                return parser.parse(matcher);
            }
        };
    }

    private interface Generator {
        @Nullable
        Action<? super ClientGameActionHandler> generate();
    }

    private static Command<ClientGameActionHandler> command(@NotNull String pattern, Generator gen) {
        return new Command<>(pattern) {
            @Override
            protected @Nullable Action<? super ClientGameActionHandler> parse(@NotNull Matcher matcher) {
                return gen.generate();
            }
        };
    }

}
