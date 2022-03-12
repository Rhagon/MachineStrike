package machinestrike.client.console.renderer.component;

import machinestrike.client.console.renderer.Graphics;
import machinestrike.client.console.renderer.color.Colors;
import machinestrike.client.console.renderer.Rect;
import org.jetbrains.annotations.Contract;

public class Panel extends HeapContainer {

    private char c;

    public Panel() {
        this(' ');
    }

    public Panel(char symbol) {
        c = symbol;
    }

    @Contract(pure = true)
    public char background() {
        return c;
    }

    @Contract("_ -> this")
    public Panel background(char c) {
        this.c = c;
        repaint();
        return this;
    }

    @Override
    public void paint(Graphics g) {
        renderPanel(g);
        super.paint(g);
    }

    protected void renderPanel(Graphics g) {
        Rect r = g.intersection(size());
        if(!r.noArea()) {
            g.fillRect(r, c, Colors.PANEL);
        }
    }

}
