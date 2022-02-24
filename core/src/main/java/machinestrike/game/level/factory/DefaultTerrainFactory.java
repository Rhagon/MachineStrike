package machinestrike.game.level.factory;

import machinestrike.game.level.Terrain;
import machinestrike.game.Trait;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Set;

public class DefaultTerrainFactory implements TerrainFactory{

    private static DefaultTerrainFactory instance;

    @NotNull
    public static DefaultTerrainFactory instance() {
        if(instance == null) {
            instance = new DefaultTerrainFactory();
        }
        return instance;
    }

    @NotNull
    public static final Trait EXCLUDE_GROUNDED = Trait.get("terrain.exclude_grounded_units"),
    IMPEDE_MOVEMENT = Trait.get("terrain.impede_movement_on_landing");

    private DefaultTerrainFactory() {
    }

    @Override
    @NotNull
    public Terrain createChasm() {
        return new Terrain("Chasm", -2, 'c', Set.of(EXCLUDE_GROUNDED));
    }

    @Override
    @NotNull
    public Terrain createMarsh() {
        return new Terrain("Marsh", -1, 'm', Set.of(IMPEDE_MOVEMENT));
    }

    @Override
    @NotNull
    public Terrain createGrassland() {
        return new Terrain("Grassland", 0, 'G', Collections.emptySet());
    }

    @Override
    @NotNull
    public Terrain createForest() {
        return new Terrain("Forest", 1, 'F', Collections.emptySet());
    }

    @Override
    @NotNull
    public Terrain createHill() {
        return new Terrain("Hill", 2, 'H', Collections.emptySet());
    }

    @Override
    @NotNull
    public Terrain createMountain() {
        return new Terrain("Mountain", 3, 'M', Collections.emptySet());
    }

}
