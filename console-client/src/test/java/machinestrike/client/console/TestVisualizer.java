package machinestrike.client.console;

import machinestrike.client.console.renderer.Renderer;
import machinestrike.client.console.renderer.DefaultFieldFormatter;
import machinestrike.client.console.renderer.FieldSection;
import machinestrike.client.console.renderer.RenderPrintStream;
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
        Board board = DefaultBoardFactory.instance().createStandardBoard(DefaultTerrainFactory.instance());
        Renderer visualizer = new Renderer(board, null, new RenderPrintStream(System.out), 10, 4, 20, DefaultFieldFormatter.instance());
        DefaultMachineFactory factory = DefaultMachineFactory.instance();
        board.field(3, 1).machine(factory.createBurrower(Player.BLUE, Orientation.NORTH));
        board.field(3, 6).machine(factory.createBurrower(Player.RED, Orientation.SOUTH));
        Field field = new Field(board, new Point(0, 0), DefaultTerrainFactory.instance().createGrassland(), null);
        FieldSection section = DefaultFieldFormatter.instance().formatField(field);
        System.out.println(section);
        visualizer.render();
    }

}
