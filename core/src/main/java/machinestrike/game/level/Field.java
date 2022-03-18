package machinestrike.game.level;

import machinestrike.debug.Assert;
import machinestrike.util.Point;
import machinestrike.game.machine.Machine;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Field {

    @NotNull
    private final Board board;
    @NotNull
    private final Point position;
    @NotNull
    private Terrain terrain;
    @Nullable
    private Machine machine;

    public Field(@NotNull Board board, @NotNull Point position, @NotNull Terrain terrain, @Nullable Machine machine) {
        this.board = board;
        this.position = position;
        Assert.range(0, position.x(), board.sizeX() - 1);
        Assert.range(0, position.y(), board.sizeY() - 1);
        this.terrain = terrain;
        this.machine = machine;
    }

    @NotNull
    @Contract(pure = true)
    public Board board() {
        return board;
    }

    @NotNull
    @Contract(pure = true)
    public Point position() {
        return position;
    }

    @NotNull
    @Contract(pure = true)
    public Terrain terrain() {
        return terrain;
    }

    @NotNull
    @Contract("_ -> this")
    public Field terrain(@NotNull Terrain terrain) {
        this.terrain = terrain;
        return this;
    }

    @Nullable
    @Contract(pure = true)
    public Machine machine() {
        return machine;
    }

    @NotNull
    @Contract("_ -> this")
    public Field machine(@Nullable Machine machine) {
        if(this.machine == machine) {
            return this;
        }
        Machine oldMachine = this.machine;
        this.machine = machine;
        if(oldMachine != null) {
            oldMachine.field(null);
        }
        if(machine != null) {
            machine.field(this);
        }
        return this;
    }

    @Override
    @Contract(pure = true)
    public String toString() {
        return "[" + terrain + (machine != null ? machine.toString() : "") + ']';
    }

}
