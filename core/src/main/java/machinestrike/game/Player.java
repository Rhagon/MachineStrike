package machinestrike.game;

import org.jetbrains.annotations.NotNull;

public enum Player {
    BLUE,
    RED;

    @NotNull
    public Player opponent() {
        return switch(this) {
            case BLUE -> RED;
            case RED -> BLUE;
        };
    }
}
