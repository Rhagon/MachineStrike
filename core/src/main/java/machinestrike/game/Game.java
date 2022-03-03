package machinestrike.game;

import machinestrike.debug.Assert;
import machinestrike.game.action.Action;
import machinestrike.game.action.AttackAction;
import machinestrike.game.action.MoveAction;
import machinestrike.game.level.Board;
import machinestrike.game.machine.Machine;
import machinestrike.game.rule.RuleBook;
import machinestrike.game.rule.RuleViolation;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Game {

    @NotNull
    private final Board board;
    @NotNull
    private Player playerOnTurn;
    @NotNull
    private final RuleBook ruleBook;
    @NotNull
    private final Set<Machine> usedMachines;
    @NotNull
    private final HashMap<Player, Integer> victoryPoints;
    @Nullable
    private Player winner;

    public Game(@NotNull Board board, @NotNull Player playerOnTurn, @NotNull RuleBook ruleBook) {
        this.board = board;
        board.game(this);
        this.playerOnTurn = playerOnTurn;
        this.ruleBook = ruleBook;
        this.usedMachines = new HashSet<>();
        this.victoryPoints = new HashMap<>();
        victoryPoints.put(Player.BLUE, 0);
        victoryPoints.put(Player.RED, 0);
    }

    @NotNull
    @Contract(pure = true)
    public Board board() {
        return board;
    }

    @NotNull
    @Contract(pure = true)
    public Player playerOnTurn() {
        return playerOnTurn;
    }

    @NotNull
    @Contract("_ -> this")
    public Game playerOnTurn(Player playerOnTurn) {
        this.playerOnTurn = playerOnTurn;
        return this;
    }

    @NotNull
    @Contract(pure = true)
    public RuleBook ruleBook() {
        return ruleBook;
    }

    public void usedMachine(Machine machine) {
        usedMachines.add(machine);
    }

    @Contract(pure = true)
    public Set<Machine> usedMachines() {
        return Collections.unmodifiableSet(usedMachines);
    }

    @Nullable
    @Contract(pure = true)
    public Player winner() {
        return winner;
    }

    public void checkWinCondition(Player player) {
        if(victoryPoints.get(player) >= ruleBook.requiredVictoryPoints()) {
            winner = player;
        }
    }

    public void endTurn() {
        for(Machine machine : usedMachines) {
            machine.resetActions();
        }
        usedMachines.clear();
        playerOnTurn = playerOnTurn.opponent();
    }

    public void addVictoryPoints(@NotNull Player player, int points) {
        victoryPoints.put(player, victoryPoints.get(player) + points);
        checkWinCondition(player);
    }

    public void execute(Action action) throws RuleViolation {
        action.execute(this);
    }

    public void handle(@NotNull MoveAction action) throws RuleViolation {
        ruleBook.verifyMove(this, action);
        Machine machine = board.field(action.origin()).machine();
        Assert.requireNotNull(machine);
        machine.move(action);
    }

    public void handle(@NotNull AttackAction action) throws RuleViolation {
        ruleBook.verifyAttack(this, action);
        Machine machine = board.field(action.origin()).machine();
        Assert.requireNotNull(machine);
        machine.attack(action);
    }

}
