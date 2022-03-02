package machinestrike.game.level;

import machinestrike.debug.Assert;
import machinestrike.game.Point;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public class Board implements Iterable<Field> {

    public class BoardIterator implements Iterator<Field> {

        private int x = 0, y = 0;

        @Override
        public boolean hasNext() {
            return y < sizeY;
        }

        @Override
        public Field next() {
            Field f = field(x++, y);
            if(x >= sizeX) {
                x = 0;
                ++y;
            }
            return f;
        }
    }

    private final int sizeX, sizeY;
    @NotNull
    private final Field[][] fields;

    public Board(Terrain[][] terrain) {
        this.sizeX = terrain.length;
        Assert.less(0, sizeX);
        this.sizeY = terrain[0].length;
        Assert.less(0, sizeY);
        fields = new Field[sizeX][sizeY];
        for(int i = 0; i < sizeX; ++i) {
            Assert.equal(fields[i].length, sizeY);
            for(int j = 0; j < sizeY; ++j) {
                fields[i][j] = new Field(i, j, terrain[i][j], null);
            }
        }
    }

    public Board(int sizeX, int sizeY, Terrain terrain) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        Assert.less(0, sizeX);
        Assert.less(0, sizeY);
        fields = new Field[sizeX][sizeY];
        for(int i = 0; i < sizeX; ++i) {
            for (int j = 0; j < sizeY; ++j) {
                fields[i][j] = new Field(i, j, terrain, null);
            }
        }
    }

    public int sizeX() {
        return sizeX;
    }

    public int sizeY() {
        return sizeY;
    }

    @NotNull
    public Field field(int posX, int posY) {
        Assert.range(0, posX, sizeX - 1);
        Assert.range(0, posY, sizeY - 1);
        return fields[posX][posY];
    }

    @NotNull
    public Field field(@NotNull Point point) {
        return field(point.x(), point.y());
    }

    @NotNull
    public Board clearMachines() {
        for(Field field : this) {
            field.machine(null);}
        return this;
    }

    @NotNull
    public Board fillTerrain(Terrain terrain) {
        for(Field field : this) {
            field.terrain(terrain).machine(null);
        }
        return this;
    }

    @NotNull
    @Override
    public Iterator<Field> iterator() {
        return new BoardIterator();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(int y = sizeY - 1; y >= 0; --y) {
            for(int x = 0; x < sizeX; ++x) {
                builder.append(field(x, y));
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
