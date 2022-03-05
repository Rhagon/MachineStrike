package machinestrike.client.console.renderer.component;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface GenericContainer extends Container {

    @Contract("_ -> this")
    Container add(@NotNull Component child);

    @Contract("_, _ -> this")
    Container add(@NotNull Component child, int index);

}
