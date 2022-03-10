package machinestrike.client.console.input;

import java.util.List;

public interface CommandListFactory<HandlerType> {

    List<Command<? super HandlerType>> create();

}
