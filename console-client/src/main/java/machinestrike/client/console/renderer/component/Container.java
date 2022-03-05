package machinestrike.client.console.renderer.component;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Container {

    @Contract(pure = true)
    List<Component> children();

    @Contract(pure = true)
    int childCount();

    void updatePrefSize();

    @Contract("_ -> this")
    Container remove(@NotNull Component child);

}
