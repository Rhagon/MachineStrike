package machinestrike.client.console;

import machinestrike.client.console.renderer.BoardRenderer;
import machinestrike.client.console.renderer.DefaultFieldFormatter;
import machinestrike.client.console.renderer.FieldSection;
import machinestrike.game.Orientation;
import machinestrike.game.Player;
import machinestrike.game.Point;
import machinestrike.game.level.Board;
import machinestrike.game.level.Field;
import machinestrike.game.level.factory.DefaultBoardFactory;
import machinestrike.game.level.factory.DefaultTerrainFactory;
import machinestrike.game.machine.factory.DefaultMachineFactory;

public class TestVisualizer {

    public static void main(String[] args) {
        BoardRenderer visualizer = new BoardRenderer(10, 4, new DefaultFieldFormatter());
        Board board = DefaultBoardFactory.instance().createStandardBoard();
        DefaultMachineFactory factory = DefaultMachineFactory.instance();
        board.field(3, 1).machine(factory.createBurrower(Player.BLUE, Orientation.NORTH));
        board.field(3, 6).machine(factory.createBurrower(Player.RED, Orientation.SOUTH));
        Field field = new Field(board, new Point(0, 0), DefaultTerrainFactory.instance().createGrassland(), null);
        FieldSection section = new DefaultFieldFormatter().formatField(field);
        System.out.println(section);
        System.out.println(visualizer.printBoard(board));
    }

}
