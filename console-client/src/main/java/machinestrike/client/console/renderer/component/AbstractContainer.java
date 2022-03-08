package machinestrike.client.console.renderer.component;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractContainer extends Component implements GenericContainer {

    @NotNull
    private final List<Component> children;

    protected AbstractContainer() {
        children = new ArrayList<>();
    }

    @Contract("_ -> this")
    public AbstractContainer add(@NotNull Component child) {
        children.add(child);
        child.parent(this);
        updatePrefSize();
        return this;
    }

    @Contract("_, _ -> this")
    public AbstractContainer add(@NotNull Component child, int index) {
        children.add(index, child);
        child.parent(this);
        updatePrefSize();
        return this;
    }

    @Contract("_ -> this")
    public AbstractContainer remove(@NotNull Component child) {
        if(!children.contains(child)) {
            return this;
        }
        children.remove(child);
        child.parent(null);
        updatePrefSize();
        return this;
    }

    @Contract(pure = true)
    public List<Component> children() {
        return Collections.unmodifiableList(children);
    }

    @Contract(pure = true)
    public int childCount() {
        return children.size();
    }

    @Contract(pure = true)
    @Nullable
    public Component child(int index){
        if(index < children.size()) {
            return children.get(index);
        }
        return null;
    }

}
