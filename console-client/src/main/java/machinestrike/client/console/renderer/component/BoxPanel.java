package machinestrike.client.console.renderer.component;

import machinestrike.client.console.renderer.Graphics;
import machinestrike.client.console.renderer.color.ColorKey;
import machinestrike.client.console.renderer.color.Colors;
import machinestrike.client.console.renderer.Rect;
import machinestrike.game.Point;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class BoxPanel extends Panel {

    public record Outline(char horizontal, char vertical, char corner, int width, int height, @NotNull ColorKey color) {

        public Outline(char horizontal, char vertical, char corner, int width, int height) {
            this(horizontal, vertical, corner, width, height, Colors.BOX);
        }

    }

    private Outline outline;

    public BoxPanel(@NotNull Outline outline) {
        this(' ', outline);
    }

    public BoxPanel(char background, Outline outline) {
        super(background);
        this.outline = outline;
    }

    @Contract(pure = true)
    @NotNull
    public Outline outline() {
        return outline;
    }

    @Contract("_ -> this")
    public BoxPanel outline(@NotNull Outline outline) {
        this.outline = outline;
        repaint();
        return this;
    }

    @Override
    public void renderPanel(Graphics g) {
        super.renderPanel(g);
        Rect section = g.intersection(size());
        if(section.noArea()) {
            return;
        }
        Rect topRect = new Rect(Point.ZERO, new Point(size().x(), outline.height));
        Rect bottomRect = new Rect(new Point(0, size().y() - outline.height), new Point(size().x(), outline.height));
        Rect leftRect = new Rect(Point.ZERO, new Point(outline.width, size().y()));
        Rect rightRect = new Rect(new Point(size().x() - outline.width, 0), new Point(outline.width, size().y()));
        g.fillRect(topRect.intersection(section), outline.horizontal, outline.color());
        g.fillRect(bottomRect.intersection(section), outline.horizontal, outline.color());
        g.fillRect(leftRect.intersection(section), outline.vertical, outline.color());
        g.fillRect(rightRect.intersection(section), outline.vertical, outline.color());
        Rect topLeftCorner = topRect.intersection(leftRect);
        Rect topRightCorner = topRect.intersection(rightRect);
        Rect bottomLeftCorner = bottomRect.intersection(leftRect);
        Rect bottomRightCorner = bottomRect.intersection(rightRect);
        g.fillRect(topLeftCorner.intersection(section), outline.corner, outline.color());
        g.fillRect(topRightCorner.intersection(section), outline.corner, outline.color());
        g.fillRect(bottomLeftCorner.intersection(section), outline.corner, outline.color());
        g.fillRect(bottomRightCorner.intersection(section), outline.corner, outline.color());
    }

}
