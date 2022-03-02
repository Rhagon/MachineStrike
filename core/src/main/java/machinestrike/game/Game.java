package machinestrike.game;

import machinestrike.game.level.Board;
import machinestrike.game.rule.RuleBook;
import org.jetbrains.annotations.NotNull;

public class Game {

    @NotNull
    private final Board board;
    @NotNull
    private Player playerOnTurn;
    @NotNull
    private final RuleBook ruleBook;

    public Game(@NotNull Board board, @NotNull Player playerOnTurn, @NotNull RuleBook ruleBook) {
        this.board = board;
        this.playerOnTurn = playerOnTurn;
        this.ruleBook = ruleBook;
    }

    @NotNull
    public Board board() {
        return board;
    }

    @NotNull
    public Player playerOnTurn() {
        return playerOnTurn;
    }

    @NotNull
    public Game playerOnTurn(Player playerOnTurn) {
        this.playerOnTurn = playerOnTurn;
        return this;
    }

    @NotNull
    public RuleBook ruleBook() {
        return ruleBook;
    }

}
