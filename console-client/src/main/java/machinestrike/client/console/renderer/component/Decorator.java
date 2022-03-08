package machinestrike.client.console.renderer.component;

import machinestrike.debug.Assert;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.Collections;
import java.util.List;

public abstract class Decorator extends Container {

    @Nullable
    private Component child;

    public Decorator() {
        this.child = null;
    }

    @Nullable
    public Component child() {
        return child;
    }

    public boolean child(@Nullable Component child) {
        if(child != null && child.parent() != null) {
            return false;
        }
        if(this.child != null) {
            Assert.isTrue(this.child.parent(null));
        }
        if(child != null) {
            Assert.isTrue(child.parent(this));
        }
        this.child = child;
        if(this.child != null) {
            this.child.updateLayout();
        }
        return true;
    }

    @Override
    @NotNull
    @UnmodifiableView
    public List<Component> children() {
        if(child != null) {
            return List.of(child);
        } else {
            return Collections.emptyList();
        }
    }
}
