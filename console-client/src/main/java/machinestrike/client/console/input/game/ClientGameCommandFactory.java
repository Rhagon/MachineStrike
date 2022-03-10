package machinestrike.client.console.input.game;

import machinestrike.client.console.action.game.ClientGameActionHandler;
import machinestrike.client.console.input.Command;
import machinestrike.client.console.input.CommandListFactory;
import machinestrike.client.console.input.client.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

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
                NewGameCommand.instance(),
                QuitCommand.instance(),
                RedrawCommand.instance(),
                SetWindowSizeCommand.instance(),
                UnknownCommand.instance()
        );
    }

}
