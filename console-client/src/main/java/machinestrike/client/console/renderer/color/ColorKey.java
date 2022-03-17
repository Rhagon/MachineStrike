package machinestrike.client.console.renderer.color;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public final class ColorKey {

    private static HashMap<String, ColorKey> keys;

    public static ColorKey get(@NotNull String name) {
        if(keys == null) {
            keys = new HashMap<>();
        }
        if(!keys.containsKey(name)) {
            keys.put(name, new ColorKey(name));
        }
        return keys.get(name);
    }

    @NotNull
    private final String name;

    private ColorKey(@NotNull String name) {
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
