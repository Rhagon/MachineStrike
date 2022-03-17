package machinestrike.game;

import machinestrike.action.ActionExecutionFailure;
import machinestrike.debug.Assert;
import machinestrike.game.action.AttackAction;
import machinestrike.game.action.EndTurnAction;
import machinestrike.game.action.MoveAction;
import machinestrike.game.level.Board;
import machinestrike.game.level.factory.DefaultBoardFactory;
import machinestrike.game.level.factory.DefaultTerrainFactory;
import machinestrike.game.machine.Machine;
import machinestrike.game.machine.factory.DefaultMachineFactory;
import machinestrike.game.rule.factory.DefaultRuleBookFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameStateMachineTests {

    private final Point blueMachine = new Point(0, 0);
    private final Point redMachine = new Point(0, 1);

    private Board board;
    private Game game;

    @BeforeEach
    public void setup() {
        Assert.push(Assert.Level.SEVERE);
        board = DefaultBoardFactory.instance().createStandardBoard(new Point(3, 3), DefaultTerrainFactory.instance());
        game = new Game(board, Player.BLUE, DefaultRuleBookFactory.instance().createRuleBook());
    }

    @AfterEach
    public void cleanUp() {
        Assert.pop();
    }

    private void placePieces() {
        board.field(blueMachine).machine(DefaultMachineFactory.instance().createBurrower(Player.BLUE, Orientation.NORTH));
        board.field(redMachine).machine(DefaultMachineFactory.instance().createBurrower(Player.RED, Orientation.SOUTH));
    }

    @Test
    public void testConstruction() {
        Assertions.assertSame(game, board.game());
        Assertions.assertSame(InactiveState.class, game.state().getClass());
        Assertions.assertThrows(AssertionError.class, () -> new Game(board, Player.BLUE, DefaultRuleBookFactory.instance().createRuleBook()));
    }

    @Test
    public void testActionsInIdleState() {
        placePieces();
        MoveAction move = new MoveAction(blueMachine, new Point(1, 0), Orientation.EAST, false);
        Assertions.assertThrows(ActionExecutionFailure.class, () -> game.execute(move));
        AttackAction attack = new AttackAction(blueMachine);
        Assertions.assertThrows(ActionExecutionFailure.class, () -> game.execute(attack));
        EndTurnAction endTurn = new EndTurnAction();
        Assertions.assertThrows(ActionExecutionFailure.class, () -> game.execute(endTurn));
        Assertions.assertDoesNotThrow(() -> game.attack(attack));
        Assertions.assertDoesNotThrow(() -> game.move(move));
        Assertions.assertDoesNotThrow(() -> game.endTurn(endTurn));
        Machine red = board.field(redMachine).machine();
        Assertions.assertNotNull(red);
        Assertions.assertEquals(3, red.health());
        Assertions.assertNotNull(board.field(new Point(1, 0)).machine());
        Assertions.assertSame(Player.RED, game.playerOnTurn());
    }

    @Test
    public void testStartGame() {
        game.start();
        Assertions.assertSame(PlayState.class, game.state().getClass());
    }

    @Test
    public void testMoveActionInPlayState() {
        placePieces();
        game.start();
        Machine machine = board.field(this.blueMachine).machine();
        Assertions.assertNotNull(machine);
        Assertions.assertDoesNotThrow(() -> game.execute(new MoveAction(this.blueMachine, new Point(1, 0), Orientation.NORTH, false)));
        Assertions.assertSame(machine, board.field(new Point(1, 0)).machine());
        Assertions.assertTrue(game.canUseMachine(machine));
        Assertions.assertFalse(game.canMove(machine));
        Assertions.assertTrue(game.canAttack(machine));
    }



}
