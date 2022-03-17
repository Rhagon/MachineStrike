package machinestrike.game;

import org.jetbrains.annotations.NotNull;

public class FinishState extends InactiveState {

    private final Player winner;

    public FinishState(@NotNull Game game, @NotNull Player winner) {
        super(game);
        this.winner = winner;
    }

    @Override
    @NotNull
    public Player winner() {
        return winner;
    }
}
