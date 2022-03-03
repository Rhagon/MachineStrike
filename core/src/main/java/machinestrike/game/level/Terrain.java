package machinestrike.game.level;

import machinestrike.game.Trait;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Terrain {

    public static final Trait EXCLUDE_GROUNDED = Trait.get("terrain.exclude_grounded_units"),
            IMPEDE_MOVEMENT = Trait.get("terrain.impede_movement_on_landing");

    @NotNull
    private final String name;
    private final int strengthModifier;
    @NotNull
    private final Set<Trait> traits;

    public Terrain(@NotNull String name, int strengthModifier, @NotNull Set<Trait> traits) {
        this.name = name;
        this.strengthModifier = strengthModifier;
        this.traits = new HashSet<>(traits);
    }

    @NotNull
    public String name() {
        return name;
    }

    public int strengthModifier() {
        return strengthModifier;
    }

    @NotNull
    @UnmodifiableView
    public Set<Trait> traits() {
        return Collections.unmodifiableSet(traits);
    }

    public boolean is(@NotNull Trait trait) {
        return traits.contains(trait);
    }



}
