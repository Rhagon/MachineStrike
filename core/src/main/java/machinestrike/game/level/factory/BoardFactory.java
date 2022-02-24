package machinestrike.game.level.factory;

import machinestrike.game.level.Board;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public interface BoardFactory {

    @NotNull
    Board createStandardBoard();

    @NotNull
    default Board createRandomBoard(@NotNull Random random) {
        return createStandardBoard();
    }

    @NotNull
    TerrainFactory terrainFactory();

}
