package machinestrike.game;

import machinestrike.game.action.GameActionHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class GameState implements GameActionHandler {

    private final Game game;

    public GameState(@NotNull Game game) {
        this.game = game;
    }

    @NotNull
    public Game game() {
        return game;
    }

    @Nullable
    public Player winner() {
        return null;
    }

}
