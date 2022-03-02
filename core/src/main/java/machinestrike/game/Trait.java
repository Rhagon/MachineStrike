package machinestrike.game;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Objects;

public class Trait {

    private static HashMap<String, Trait> traits;

    @NotNull
    public static Trait get(@NotNull String name) {
        if(traits == null) {
            traits = new HashMap<>();
        }
        if(!traits.containsKey(name)) {
            traits.put(name, new Trait(name));
        }
        return traits.get(name);
    }

    @NotNull
    private final String name;

    private Trait(@NotNull String name) {
        this.name = name;
    }

    @NotNull
    public String name() {
        return name;
    }

    @Override
    public boolean equals(Object second) {
        return this == second;
    }

    @Override
    public String toString() {
        return name;
    }

}
