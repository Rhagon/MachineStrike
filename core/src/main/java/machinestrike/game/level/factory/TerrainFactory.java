package machinestrike.game.level.factory;

import machinestrike.game.level.Terrain;
import org.jetbrains.annotations.NotNull;

public interface TerrainFactory {

    @NotNull
    Terrain createChasm();

    @NotNull
    Terrain createMarsh();

    @NotNull
    Terrain createGrassland();

    @NotNull
    Terrain createForest();

    @NotNull
    Terrain createHill();

    @NotNull
    Terrain createMountain();

}
