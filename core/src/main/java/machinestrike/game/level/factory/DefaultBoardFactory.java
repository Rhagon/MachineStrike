package machinestrike.game.level.factory;

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
    public Board createStandardBoard(@NotNull TerrainFactory terrainFactory) {
        return new Board(8, 8, terrainFactory.createGrassland());
    }

}
