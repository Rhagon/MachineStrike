package machinestrike.game.level.factory;

import machinestrike.game.level.Terrain;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface TerrainFactory {

    @NotNull
    List<String> names();

    @Nullable
    Terrain forName(@NotNull String name);

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
