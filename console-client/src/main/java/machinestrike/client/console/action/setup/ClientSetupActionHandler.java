package machinestrike.client.console.action.setup;

import machinestrike.client.console.action.client.ClientActionHandler;
import org.jetbrains.annotations.NotNull;

public interface ClientSetupActionHandler extends ClientActionHandler, SetupActionHandler {

    void handle(@NotNull StartGameAction action);

}
