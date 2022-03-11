package machinestrike.client.console.renderer.component;

import machinestrike.client.console.renderer.shape.Rect;
import machinestrike.game.Point;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Label extends Component {

    public enum Alignment {
        TOP_LEFT(0),
        TOP_CENTER(1),
        TOP_RIGHT(2),
        CENTER_LEFT(3),
        CENTER(4),
        CENTER_RIGHT(5),
        BOTTOM_LEFT(6),
        BOTTOM_CENTER(7),
        BOTTOM_RIGHT(8);

        private final int index;

        Alignment(int index) {
            this.index = index;
        }

        public boolean top() {
            return index / 3 == 0;
        }

        public boolean verticalCenter() {
            return index / 3 == 1;
        }

        public boolean bottom() {
            return index / 3 == 2;
        }

        public boolean left() {
            return index % 3 == 0;
        }

        public boolean horizontalCenter() {
            return index % 3 == 1;
        }

        public boolean right() {
            return index % 3 == 2;
        }

    }

    public enum OverflowPolicy {
        TRUNC_LEFT,
        TRUNC_RIGHT,
        WRAP
    }

    @NotNull
    private String text;
    private String[] lines;
    @NotNull
    private Alignment alignment;
    @NotNull
    private OverflowPolicy policy;
    @NotNull
    private Color color;

    public Label() {
        this("", Color.WHITE);
    }

    public Label(@NotNull String text, @NotNull Color color) {
        this(text, Alignment.TOP_LEFT, color);
    }

    public Label(@NotNull String text, @NotNull Alignment alignment, @NotNull Color color) {
        this(text, alignment, OverflowPolicy.WRAP, color);
    }

    public Label(@NotNull String text, @NotNull Alignment alignment, @NotNull OverflowPolicy policy, @NotNull Color color) {
        this.text = text;
        this.alignment = alignment;
        this.policy = policy;
        this.color = color;
        update();
    }

    @NotNull
    public Color color() {
        return color;
    }

    public void color(@NotNull Color color) {
        this.color = color;
        repaint();
    }

    @Contract(pure = true)
    @NotNull
    public String text() {
        return text;
    }

    @Contract("_ -> this")
    public Label text(@Nullable String text) {
        this.text = Objects.requireNonNullElse(text, "");
        update();
        return this;
    }

    @Contract(pure = true)
    @NotNull
    public Alignment alignment() {
        return alignment;
    }

    @Contract("_ -> this")
    public Label alignment(@NotNull Alignment alignment) {
        this.alignment = alignment;
        update();
        return this;
    }

    @Contract(pure = true)
    @NotNull
    public OverflowPolicy policy() {
        return policy;
    }

    @Contract("_ -> this")
    public Label policy(@NotNull OverflowPolicy policy) {
        this.policy = policy;
        update();
        return this;
    }

    private void update() {
        if(size().x() <= 0) {
            lines = new String[0];
            return;
        }
        if(policy == OverflowPolicy.WRAP) {
            List<String> lines = new ArrayList<>();
            for (String line : text.lines().toArray(String[]::new)) {
                String remaining = line;
                while(remaining != null) {
                    if(remaining.length() > size().x()) {
                        lines.add(remaining.substring(0, size().x()));
                        remaining = remaining.substring(size().x());
                    } else {
                        lines.add(remaining);
                        remaining = null;
                    }
                }
            }
            String[] allLines = lines.toArray(String[]::new);
            if(allLines.length > size().y()) {
                this.lines = Arrays.copyOfRange(allLines, 0, size().y());
            } else {
                this.lines = allLines;
            }
        } else {
            lines = text.lines().toArray(String[]::new);
            for(int i = 0; i < lines.length; ++i) {
                if(lines[i].length() > size().x()) {
                    lines[i] = switch(policy) {
                        case TRUNC_LEFT -> lines[i].substring(lines[i].length() - size().x());
                        case TRUNC_RIGHT -> lines[i].substring(0, size().x());
                        case WRAP -> throw new IllegalStateException("Unexpected enum value: " + policy.name());
                    };
                }
            }
        }
        repaint();
    }

    @Override
    protected void onLayoutChange() {
        update();
    }

    @Override
    public void paint(Graphics g) {
        Rect intersection = g.intersection(size());
        if(intersection.noArea()) {
            return;
        }
        int labelHeight = size().y();
        int textHeight = lines.length;
        int startLine = 0;
        if(alignment.bottom()) {
            startLine = labelHeight - textHeight;
        }
        if(alignment.verticalCenter()) {
            startLine = (labelHeight - textHeight) / 2;
        }
        for(int i = 0; i < lines.length; ++i) {
            int lineStart = 0;
            if(alignment.right()) {
                lineStart = size().x() - lines[i].length();
            }
            if(alignment.horizontalCenter()) {
                lineStart = (size().x() - lines[i].length()) / 2;
            }
            g.printString(new Point(lineStart, startLine + i), lines[i], color);
        }
    }

}
