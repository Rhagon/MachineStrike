package machinestrike.game.level.factory;

import machinestrike.util.Point;
import machinestrike.game.level.Board;
import org.jetbrains.annotations.NotNull;

public class DefaultBoardFactory implements BoardFactory{

    private static DefaultBoardFactory instance;

    public static DefaultBoardFactory instance() {
        if(instance == null) {
            instance = new DefaultBoardFactory();
        }
        return instance;
    }

    private DefaultBoardFactory() {
    }

    @Override
    @NotNull
    public Board createStandardBoard(@NotNull Point size, @NotNull TerrainFactory terrainFactory) {
        return new Board(size.x(), size.y(), terrainFactory.createGrassland());
    }

    @Override
    public @NotNull Board createStandardBoard(@NotNull TerrainFactory terrainFactory) {
        return createStandardBoard(new Point(8, 8), terrainFactory);
    }

}
