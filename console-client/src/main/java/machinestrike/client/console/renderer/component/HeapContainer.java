package machinestrike.client.console.renderer.component;

import machinestrike.debug.Assert;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class HeapContainer extends Container {

    private final List<Component> children;

    public HeapContainer() {
        this.children = new ArrayList<>();
    }

    public boolean add(@NotNull Component child) {
        if(child.parent(this)) {
            children.add(child);
            return true;
        }
        return false;
    }

    public boolean add(@NotNull Component child, int index) {
        if(index >= children.size()) {
            return false;
        }
        if(child.parent(this)) {
            children.add(index, child);
            return true;
        }
        return false;
    }

    public boolean remove(@NotNull Component child) {
        if(children.contains(child)) {
            children.remove(child);
            Assert.isTrue(child.parent(null));
            return true;
        }
        return false;
    }

    public boolean remove(int index) {
        if(children.size() <= index) {
            return false;
        }
        Component child = children.remove(index);
        Assert.isTrue(child.parent(null));
        return true;
    }

    @Override
    @UnmodifiableView
    @NotNull
    public List<Component> children() {
        return Collections.unmodifiableList(children);
    }

}
