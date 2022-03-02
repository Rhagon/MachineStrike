package machinestrike.game.level.factory;

import machinestrike.game.level.Terrain;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Set;

import static machinestrike.game.level.Terrain.*;

public class DefaultTerrainFactory implements TerrainFactory{

    private static DefaultTerrainFactory instance;

    @NotNull
    public static DefaultTerrainFactory instance() {
        if(instance == null) {
            instance = new DefaultTerrainFactory();
        }
        return instance;
    }

    private DefaultTerrainFactory() {
    }

    @Override
    @NotNull
    public Terrain createChasm() {
        return new Terrain("Chasm", -2, Set.of(EXCLUDE_GROUNDED));
    }

    @Override
    @NotNull
    public Terrain createMarsh() {
        return new Terrain("Marsh", -1, Set.of(IMPEDE_MOVEMENT));
    }

    @Override
    @NotNull
    public Terrain createGrassland() {
        return new Terrain("Grassland", 0, Collections.emptySet());
    }

    @Override
    @NotNull
    public Terrain createForest() {
        return new Terrain("Forest", 1, Collections.emptySet());
    }

    @Override
    @NotNull
    public Terrain createHill() {
        return new Terrain("Hill", 2, Collections.emptySet());
    }

    @Override
    @NotNull
    public Terrain createMountain() {
        return new Terrain("Mountain", 3, Collections.emptySet());
    }

}
