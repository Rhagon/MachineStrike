package machinestrike.client.console.renderer.component;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.List;

public abstract class Container extends Component {

    public Container() {
    }

    @NotNull
    @UnmodifiableView
    public abstract List<Component> children();

    @Override
    public void paint(Graphics g) {
        for(Component c : children()) {
            Graphics cRelative = g.create(c.position());
            c.paint(cRelative);
        }
    }

    @Override
    public void onLayoutChange() {
        for(Component c : children()) {
            c.onLayoutChange();
        }
    }

}
