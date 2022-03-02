package machinestrike.game.level;

import machinestrike.debug.Assert;
import machinestrike.game.Point;
import machinestrike.game.machine.Machine;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Field {

    private final int posX, posY;
    @NotNull
    private Terrain terrain;
    @Nullable
    private Machine machine;

    public Field(int posX, int posY, @NotNull Terrain terrain, @Nullable Machine machine) {
        this.posX = posX;
        this.posY = posY;
        Assert.lessOrEqual(0, posX);
        Assert.lessOrEqual(0, posY);
        this.terrain = terrain;
        this.machine = machine;
    }

    public int posX() {
        return posX;
    }

    public int posY() {
        return posY;
    }

    @NotNull
    public Point position() {
        return new Point(posX, posY);
    }

    @NotNull
    public Terrain terrain() {
        return terrain;
    }

    @NotNull
    public Field terrain(@NotNull Terrain terrain) {
        this.terrain = terrain;
        return this;
    }

    @Nullable
    public Machine machine() {
        return machine;
    }

    @NotNull
    public Field machine(@Nullable Machine machine) {
        this.machine = machine;
        return this;
    }

    @Override
    public String toString() {
        return "[" + terrain + (machine != null ? machine.toString() : "    ") + ']';
    }

}
