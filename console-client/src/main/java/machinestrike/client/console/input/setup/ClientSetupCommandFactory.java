package machinestrike.client.console.input.setup;

import machinestrike.client.console.action.setup.ClientSetupActionHandler;
import machinestrike.client.console.input.Command;
import machinestrike.client.console.input.CommandListFactory;
import machinestrike.client.console.input.client.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

public class ClientSetupCommandFactory implements CommandListFactory<ClientSetupActionHandler> {

    private static ClientSetupCommandFactory instance;

    public static ClientSetupCommandFactory instance() {
        if(instance == null) {
            instance = new ClientSetupCommandFactory();
        }
        return instance;
    }

    private ClientSetupCommandFactory() {
    }

    @NotNull
    @Unmodifiable
    public List<Command<? super ClientSetupActionHandler>> create() {
        return List.of(
                HelpCommand.instance(),
                MirrorTerrainCommand.instance(),
                NewGameCommand.instance(),
                PlaceMachineCommand.instance(),
                QuitCommand.instance(),
                RedrawCommand.instance(),
                SetTerrainCommand.instance(),
                SetWindowSizeCommand.instance(),
                StartGameCommand.instance(),
                UnknownCommand.instance()
        );
    }

}
