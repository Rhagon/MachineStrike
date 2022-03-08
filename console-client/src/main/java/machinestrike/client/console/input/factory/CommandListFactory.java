package machinestrike.client.console.input.factory;

import machinestrike.client.console.input.Command;

import java.util.List;

public interface CommandListFactory {

    List<Command<?>> createCommandList();

}
