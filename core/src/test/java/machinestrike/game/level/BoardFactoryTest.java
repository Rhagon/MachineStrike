package machinestrike.game.level;

import machinestrike.game.Orientation;
import machinestrike.game.Player;
import machinestrike.game.level.factory.BoardFactory;
import machinestrike.game.level.factory.DefaultBoardFactory;
import machinestrike.game.machine.factory.DefaultMachineFactory;

public class BoardFactoryTest {

    public static void main(String[] args) {
        BoardFactory factory = DefaultBoardFactory.instance();
        Board board = factory.createStandardBoard();
        DefaultMachineFactory machineFactory = DefaultMachineFactory.instance();
        board.field(2, 1).machine(machineFactory.createBurrower(Player.BLUE, Orientation.NORTH));
        board.field(2, 6).machine(machineFactory.createBurrower(Player.RED, Orientation.SOUTH));
        System.out.println(board);
    }

}
