package machinestrike.game.level.factory;

import machinestrike.game.level.Board;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public interface BoardFactory {

    @NotNull
    Board createStandardBoard(@NotNull TerrainFactory terrainFactory);

    @NotNull
    default Board createRandomBoard(@NotNull TerrainFactory terrainFactory, @NotNull Random random) {
        return createStandardBoard(terrainFactory);
    }

}
