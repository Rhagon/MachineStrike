package machinestrike.client.console.command.factory;

import machinestrike.client.console.action.setup.SetupActionHandler;
import machinestrike.client.console.command.Command;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayList;
import java.util.List;

public class SetupCommandFactory {

    private static SetupCommandFactory instance;

    public static SetupCommandFactory instance() {
        if(instance == null) {
            instance = new SetupCommandFactory();
        }
        return instance;
    }

    private SetupCommandFactory() {
    }

    @NotNull
    @Unmodifiable
    public List<Command<SetupActionHandler>> create() {
        return new ArrayList<>();
    }

}
