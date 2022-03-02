package machinestrike.client.console;

import machinestrike.client.console.input.InputHandler;
import machinestrike.client.console.renderer.BoardRenderer;
import machinestrike.client.console.renderer.DefaultFieldFormatter;
import machinestrike.game.Game;
import machinestrike.game.Orientation;
import machinestrike.game.Player;
import machinestrike.game.action.Action;
import machinestrike.game.level.factory.DefaultBoardFactory;
import machinestrike.game.level.factory.DefaultTerrainFactory;
import machinestrike.game.machine.factory.DefaultMachineFactory;
import machinestrike.game.rule.RuleViolation;
import machinestrike.game.rule.factory.DefaultRuleBookFactory;
import machinestrike.game.statemachine.PlayerTurnState;
import machinestrike.game.statemachine.StateMachine;

import java.io.InputStream;
import java.io.PrintStream;

public class ConsoleClient {

    private final DefaultBoardFactory boardFactory = DefaultBoardFactory.instance();
    private final DefaultMachineFactory machineFactory = DefaultMachineFactory.instance();
    private final DefaultTerrainFactory tf = DefaultTerrainFactory.instance();

    public static void main(String[] args) {
        new ConsoleClient(System.in, System.out).run();
    }

    private final InputStream input;
    private final PrintStream output;

    public ConsoleClient(InputStream input, PrintStream output) {
        this.input = input;
        this.output = output;
    }

    public void run() {
        Game game = new Game(boardFactory.createStandardBoard(), Player.BLUE, new DefaultRuleBookFactory().createRuleBook());
        game.board().field(1, 2).machine(machineFactory.createBurrower(Player.BLUE, Orientation.NORTH));
        for(int i = 0; i <= 3; ++i) {
            game.board().field(3, i).terrain(tf.createChasm());
            game.board().field(i, 3).terrain(tf.createMarsh());
        }
        StateMachine gameMachine = new StateMachine(game);
        gameMachine.changeState(new PlayerTurnState(gameMachine, Player.BLUE));
        InputHandler handler = new InputHandler(input);
        BoardRenderer renderer = new BoardRenderer(13, 5, new DefaultFieldFormatter());
        System.out.println(renderer.printBoard(game.board()));
        for(Action action : handler) {
            try {
                gameMachine.execute(action);
                output.println(renderer.printBoard(game.board()));
            } catch (RuleViolation e) {
                output.println(e.getMessage());
            }
        }
    }

}
