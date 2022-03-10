package machinestrike.client.console.renderer.component;

import machinestrike.client.console.renderer.shape.Rect;
import machinestrike.game.Point;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public record Anchor(@NotNull Constraint horizontal, @NotNull Constraint vertical) {

    private static final int DEFAULT_WIDTH = 20, DEFAULT_HEIGHT = 7;

    public static final Anchor
            TOP_LEFT = new Anchor(Constraint.FIXED_BEGIN.size(DEFAULT_WIDTH), Constraint.FIXED_BEGIN.size(DEFAULT_HEIGHT)),
            TOP_CENTER = new Anchor(Constraint.FIXED_CENTER.size(DEFAULT_WIDTH), Constraint.FIXED_BEGIN.size(DEFAULT_HEIGHT)),
            TOP_RIGHT = new Anchor(Constraint.FIXED_END.size(DEFAULT_WIDTH), Constraint.FIXED_BEGIN.size(DEFAULT_HEIGHT)),
            CENTER_LEFT = new Anchor(Constraint.FIXED_BEGIN.size(DEFAULT_WIDTH), Constraint.FIXED_CENTER.size(DEFAULT_HEIGHT)),
            CENTER = new Anchor(Constraint.FIXED_CENTER.size(DEFAULT_WIDTH), Constraint.FIXED_CENTER.size(DEFAULT_HEIGHT)),
            CENTER_RIGHT = new Anchor(Constraint.FIXED_END.size(DEFAULT_WIDTH), Constraint.FIXED_CENTER.size(DEFAULT_HEIGHT)),
            BOTTOM_LEFT = new Anchor(Constraint.FIXED_BEGIN.size(DEFAULT_WIDTH), Constraint.FIXED_END.size(DEFAULT_HEIGHT)),
            BOTTOM_CENTER = new Anchor(Constraint.FIXED_CENTER.size(DEFAULT_WIDTH), Constraint.FIXED_END.size(DEFAULT_HEIGHT)),
            BOTTOM_RIGHT = new Anchor(Constraint.FIXED_END.size(DEFAULT_WIDTH), Constraint.FIXED_END.size(DEFAULT_HEIGHT)),

            TOP_EDGE = new Anchor(Constraint.SCALING, Constraint.FIXED_BEGIN.size(DEFAULT_HEIGHT)),
            LEFT_EDGE = new Anchor(Constraint.FIXED_BEGIN.size(DEFAULT_WIDTH), Constraint.SCALING),
            BOTTOM_EDGE = new Anchor(Constraint.SCALING, Constraint.FIXED_END.size(DEFAULT_HEIGHT)),
            RIGHT_EDGE = new Anchor(Constraint.FIXED_END.size(DEFAULT_WIDTH), Constraint.SCALING),

            AREA = new Anchor(Constraint.SCALING, Constraint.SCALING);

    public record Constraint(Type type, int position, int size, int firstPad, int secondPad) {

        public static final Constraint
                FIXED_BEGIN = new Constraint(Type.FIXED_BEGIN, 0, 0, 0, 0),
                FIXED_CENTER = new Constraint(Type.FIXED_CENTER, 0, 0, 0, 0),
                FIXED_END = new Constraint(Type.FIXED_END, 0, 0, 0, 0),
                SCALING = new Constraint(Type.SCALING, 0, 0, 0, 0);

        private interface Function {
            @NotNull
            @Contract(pure = true)
            Point calc(int parentSize, Constraint c);
        }

        public enum Type {
            FIXED_BEGIN((ps, c) -> new Point(c.position, c.size())),
            FIXED_CENTER((ps, c) -> new Point((ps - c.size()) / 2 + c.position, c.size())),
            FIXED_END((ps, c) -> new Point(ps - c.size() + c.position(), c.size())),
            SCALING((ps, c) -> new Point(c.firstPad, ps - c.firstPad - c.secondPad));

            private final Function f;

            Type(Function f) {
                this.f = f;
            }

            @NotNull
            @Contract(pure = true)
            private Point calc(int size, @NotNull Constraint c) {
                return f.calc(size, c);
            }

        }

        @NotNull
        @Contract(pure = true)
        private Point calc(int parentSize) {
            return type.calc(parentSize, this);
        }

        @NotNull
        @Contract(pure = true)
        public Constraint size(int size) {
            return new Constraint(type, position, size, firstPad, secondPad);
        }

        @NotNull
        @Contract(pure = true)
        public Constraint position(int position) {
            return new Constraint(type, position, size, firstPad, secondPad);
        }

        @NotNull
        @Contract(pure = true)
        public Constraint pad(int firstPad, int secondPad) {
            return new Constraint(type, position, size, firstPad, secondPad);
        }

    }

    @NotNull
    @Contract(pure = true)
    public Anchor size(int width, int height) {
        return new Anchor(horizontal.size(width), vertical.size(height));
    }

    @NotNull
    @Contract(pure = true)
    public Anchor position(int x, int y) {
        return new Anchor(horizontal.position(x), vertical.position(y));
    }

    @NotNull
    @Contract(pure = true)
    public Anchor pad(int top, int left, int right, int bottom) {
        return new Anchor(horizontal.pad(left, right), vertical.pad(top, bottom));
    }

    @NotNull
    @Contract(pure = true)
    public Point size() {
        return new Point(horizontal.size(), vertical().size());
    }

    @NotNull
    @Contract(pure = true)
    public Rect applyOn(@NotNull Rect rect) {
        Point xw = horizontal.calc(rect.width());
        Point yh = vertical.calc(rect.height());
        return new Rect(xw.x(), yh.x(), xw.y(), yh.y());
    }

}
