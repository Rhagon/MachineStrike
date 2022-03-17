package machinestrike.game.machine;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public final class MachineKey {

    private static HashMap<String, MachineKey> keys;

    public static MachineKey get(@NotNull String name) {
        if(keys == null) {
            keys = new HashMap<>();
        }
        if(!keys.containsKey(name.toLowerCase())) {
            keys.put(name.toLowerCase(), new MachineKey(name.toLowerCase()));
        }
        return keys.get(name.toLowerCase());
    }

    @NotNull
    private final String name;

    private MachineKey(@NotNull String name) {
        this.name = name;
    }

    @NotNull
    public String name() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

}