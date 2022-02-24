package machinestrike.game.level;

import machinestrike.game.Trait;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Terrain {

    @NotNull
    private final String name;
    private final int defaultAttackBonus;
    private final char descriptor;
    @NotNull
    private final Set<Trait> traits;

    public Terrain(@NotNull String name, int defaultAttackBonus, char descriptor, @NotNull Set<Trait> traits) {
        this.name = name;
        this.defaultAttackBonus = defaultAttackBonus;
        this.descriptor = descriptor;
        this.traits = new HashSet<>(traits);
    }

    @NotNull
    public String name() {
        return name;
    }

    public int defaultAttackBonus() {
        return defaultAttackBonus;
    }

    public char descriptor() {
        return descriptor;
    }

    @NotNull
    @UnmodifiableView
    public Set<Trait> traits() {
        return Collections.unmodifiableSet(traits);
    }

    @Override
    public String toString() {
        return "" + descriptor;
    }

}
