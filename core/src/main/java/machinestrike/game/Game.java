package machinestrike.game;

import machinestrike.action.ActionExecutionFailure;
import machinestrike.debug.Assert;
import machinestrike.game.action.AttackAction;
import machinestrike.game.action.EndTurnAction;
import machinestrike.game.action.GameActionHandler;
import machinestrike.game.action.MoveAction;
import machinestrike.game.level.Board;
import machinestrike.game.level.Field;
import machinestrike.game.machine.Machine;
import machinestrike.game.machine.MachineState;
import machinestrike.game.rule.RuleBook;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class Game implements GameActionHandler {

    @NotNull
    private final Board board;
    @NotNull
    private Player playerOnTurn;
    @NotNull
    private final RuleBook ruleBook;
    @NotNull
    private final HashMap<Machine, MachineState> usedMachines;
    @NotNull
    private final HashMap<Player, Integer> victoryPoints;
    @NotNull
    private GameState state;

    public Game(@NotNull Board board, @NotNull Player playerOnTurn, @NotNull RuleBook ruleBook) {
        this.board = board;
        board.game(this);
        this.playerOnTurn = playerOnTurn;
        this.ruleBook = ruleBook;
        this.usedMachines = new HashMap<>();
        this.victoryPoints = new HashMap<>();
        victoryPoints.put(Player.BLUE, 0);
        victoryPoints.put(Player.RED, 0);
        state = new InactiveState(this);
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
    protected Game playerOnTurn(Player playerOnTurn) {
        this.playerOnTurn = playerOnTurn;
        return this;
    }

    @NotNull
    @Contract(pure = true)
    public RuleBook ruleBook() {
        return ruleBook;
    }

    public int victoryPoints(@NotNull Player player) {
        return victoryPoints.get(player);
    }

    public void addVictoryPoints(@NotNull Player player, int points) {
        victoryPoints.put(player, victoryPoints.get(player) + points);
        checkWinCondition(player);
    }

    @Nullable
    @Contract(pure = true)
    public Player winner() {
        return state.winner();
    }

    public void checkWinCondition(Player player) {
        if(ruleBook().checkWinCondition(this, player)) {
            state = new FinishState(this, player);
        }
    }

    /**
     * Executes a move after verifying it. Also updates the machine's state, if it is not a virtual move.
     * @param action The move to execute
     * @throws ActionExecutionFailure  If the move is invalid or violates game rules
     */
    public void handle(@NotNull MoveAction action) throws ActionExecutionFailure {
        ruleBook.verifyMove(this, action);
        Machine machine = board.field(action.origin()).machine();
        Assert.requireNotNull(machine);
        move(machine, action.destination(), action.orientation());
        if(!action.virtualMove()) {
            MachineState state = useMachine(machine);
            if(!state.canMove) {
                overcharge(machine);
            }
            state.canMove = false;
            if(action.sprint()) {
                state.canAttack = false;
            }
        }
    }

    @Override
    public void handle(@NotNull EndTurnAction action) throws ActionExecutionFailure {
        usedMachines.clear();
        playerOnTurn = playerOnTurn.opponent();
    }

    public void handle(@NotNull AttackAction action) throws ActionExecutionFailure {
        ruleBook.verifyAttack(this, action);
        Machine machine = board.field(action.origin()).machine();
        Assert.requireNotNull(machine);
        machine.attack();
    }

    /**
     * Moves a machine without verification and without changing the game's state.
     * @param machine The machine to move
     * @param destination The destination for the machine
     * @param orientation The orientation that the machine should face
     * @throws AssertionError If the machine is not on the board of this game.
     */
    public void move(@NotNull Machine machine, @NotNull Point destination, @NotNull Orientation orientation) throws AssertionError {
        assertOnThisBoard(machine);
        machine.field(board().field(destination));
        machine.orientation(orientation);
    }

    public void performStandardAttack(@NotNull Machine machine, @NotNull Point origin) {
        assertOnThisBoard(machine);
        Assert.requireNotNull(machine.field());
        Assert.equal(machine.field().position(), origin);
        Field attackedField = Assert.requireNotNull(machine.firstAssailableFieldWithMachine());
        Machine attackedMachine = Assert.requireNotNull(attackedField.machine());
        int attackerPower = ruleBook().calculateStrength(machine, machine.orientation(), false);
        int defenderPower = ruleBook().calculateStrength(attackedMachine, machine.orientation().add(Orientation.SOUTH), true);
        int rawDamage = attackerPower - defenderPower;
        int damage = Math.max(1, attackerPower - defenderPower);
        boolean defenseBreak = rawDamage < 1;
        damage(attackedMachine, damage);
        if(defenseBreak) {
            knockBack(attackedMachine, machine.orientation());
            damage(machine, 1);
        }
        MachineState state = useMachine(machine);
        if(!state.canAttack) {
            overcharge(machine);
        }
        state.canAttack = false;
    }

    /**
     * Knocks back a machine and applies damage if necessary.
     * @param machine The machine that should be knocked back
     * @param knockBackDirection The direction in which the knock back should be performed
     */
    public void knockBack(@NotNull Machine machine, @NotNull Orientation knockBackDirection) {
        assertOnThisBoard(machine);
        Field destination = board().field(Assert.requireNotNull(machine.field()).position().add(knockBackDirection.asPoint()));
        Machine machineOnDestination = destination.machine();
        if(machineOnDestination != null) {
            damage(machine, 1);
            damage(machineOnDestination, 1);
            return;
        }
        MoveAction knockBackMove = new MoveAction(machine.field().position(), destination.position(), machine.orientation(), false, true);
        if(ruleBook.testMove(this, knockBackMove)) {
            Assert.requireNoThrow(() -> handle(knockBackMove));
        } else {
            damage(machine, 1);
        }
    }

    public void overcharge(@NotNull Machine machine) {
        assertOnThisBoard(machine);
        useMachine(machine).wasOvercharged = true;
        damage(machine, ruleBook().overchargeDamage());
    }

    /**
     * Applies damage to a machine and removes it, if the machine's health drops to zero.
     * @param machine The damaged machine
     * @param amount The amount of damage that the machine should receive
     */
    public void damage(@NotNull Machine machine, int amount) {
        assertOnThisBoard(machine);
        machine.health(machine.health() - amount);
        if(machine.health() <= 0) {
            machine.field(null);
            addVictoryPoints(machine.player().opponent(), machine.victoryPoints());
        }
    }

    public MachineState useMachine(@NotNull Machine machine) {
        assertOnThisBoard(machine);
        Assert.same(machine.player(), playerOnTurn);
        if(!usedMachines.containsKey(machine)) {
            usedMachines.put(machine, new MachineState());
        }
        return usedMachines.get(machine);
    }

    @Nullable
    public MachineState machineState(@NotNull Machine machine) {
        return usedMachines.get(machine);
    }

    public boolean canUseMachine(@NotNull Machine machine) {
        assertOnThisBoard(machine);
        return machineState(machine) != null || usedMachines.keySet().size() < ruleBook().machinesPerTurn();
    }

    /**
     * Determines whether a machine can move. This includes the current machine state as well
     * as the maximum machines a player can use per turn.
     */
    public boolean canMove(@NotNull Machine machine) {
        if(!canUseMachine(machine)) {
            return false;
        }
        MachineState state = machineState(machine);
        return state == null || state.canMove;
    }

    /**
     * Determines whether a machine can attack. This includes the current machine state as well
     * as the maximum machines a player can use per turn.
     */
    public boolean canAttack(@NotNull Machine machine) {
        if(!canUseMachine(machine)) {
            return false;
        }
        MachineState state = machineState(machine);
        return state == null || state.canAttack;
    }

    public boolean wasOvercharged(@NotNull Machine machine) {
        MachineState state = machineState(machine);
        return state != null && state.wasOvercharged;
    }

    /**
     * Makes sure that the machine is placed on the board of this game.
     * @param machine The machine to verify
     * @throws AssertionError If the machine is not placed on a board
     *          or is placed on another board and the assertion level is set to severe
     */
    public void assertOnThisBoard(@NotNull Machine machine) throws AssertionError {
        Assert.same(Assert.requireNotNull(machine.field()).board(), board);
    }

}
