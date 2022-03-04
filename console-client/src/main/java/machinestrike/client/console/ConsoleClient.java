package machinestrike.client.console;

import machinestrike.client.console.action.ConsoleActionHandler;
import machinestrike.client.console.action.HelpAction;
import machinestrike.client.console.action.QuitAction;
import machinestrike.client.console.input.Command;
import machinestrike.client.console.input.InputHandler;
import machinestrike.client.console.input.factory.CommandListFactory;
import machinestrike.client.console.input.factory.DefaultCommandListFactory;
import machinestrike.client.console.renderer.*;
import machinestrike.debug.Assert;
import machinestrike.game.Game;
import machinestrike.game.Orientation;
import machinestrike.game.Player;
import machinestrike.game.action.Action;
import machinestrike.game.action.GameActionHandler;
import machinestrike.game.level.Board;
import machinestrike.game.level.factory.BoardFactory;
import machinestrike.game.level.factory.DefaultBoardFactory;
import machinestrike.game.level.factory.DefaultTerrainFactory;
import machinestrike.game.level.factory.TerrainFactory;
import machinestrike.game.machine.factory.DefaultMachineFactory;
import machinestrike.game.machine.factory.MachineFactory;
import machinestrike.game.rule.RuleBook;
import machinestrike.game.rule.RuleViolation;
import machinestrike.game.rule.factory.DefaultRuleBookFactory;
import machinestrike.game.rule.factory.RuleBookFactory;
import machinestrike.util.ActionUnion;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;
import java.io.PrintStream;

public class ConsoleClient implements ConsoleActionHandler {

    public static void main(String[] args) {
        new ConsoleClient().run();
    }

    @NotNull
    private final PrintStream output;
    @NotNull
    private final InputHandler inputHandler;
    @NotNull
    private final BoardFactory boardFactory;
    @NotNull
    private final MachineFactory machineFactory;
    @NotNull
    private final TerrainFactory terrainFactory;
    @NotNull
    private final RuleBookFactory ruleBookFactory;
    @NotNull
    private final BoardRenderer renderer;
    @Nullable
    private Board board;
    @Nullable
    private Game game;

    public ConsoleClient() {
        this(System.in, new RenderPrintStream(System.out), System.out);
    }

    public ConsoleClient(@NotNull InputStream input, @NotNull RenderStream boardStream, @NotNull PrintStream output) {
        this(input, boardStream, output, DefaultCommandListFactory.instance(),
                DefaultBoardFactory.instance(), DefaultMachineFactory.instance(), DefaultTerrainFactory.instance(),
                DefaultRuleBookFactory.instance(), DefaultFieldFormatter.instance());
    }

    public ConsoleClient(@NotNull InputStream input, @NotNull RenderStream boardStream, @NotNull PrintStream output,
                         @NotNull CommandListFactory commandFactory, @NotNull BoardFactory bf, @NotNull MachineFactory mf,
                         @NotNull TerrainFactory tf, @NotNull RuleBookFactory rf, @NotNull FieldFormatter formatter) {
        this.output = output;
        this.inputHandler = new InputHandler(input, output, commandFactory.createCommandList(this));
        this.boardFactory = bf;
        this.machineFactory = mf;
        this.terrainFactory = tf;
        this.ruleBookFactory = rf;
        this.board = null;
        this.game = null;
        this.renderer = new BoardRenderer(null, boardStream, 13, 5, formatter);
    }

    public void setup() {
        Assert.requireNotNull(board);
        board.field(1, 2).machine(machineFactory.createBurrower(Player.BLUE, Orientation.NORTH));
        for(int i = 0; i <= 3; ++i) {
            board.field(3, i).terrain(terrainFactory.createChasm());
            board.field(i, 3).terrain(terrainFactory.createMarsh());
        }
    }

    public void run() {
        board = boardFactory.createStandardBoard(terrainFactory);
        RuleBook ruleBook = ruleBookFactory.createRuleBook();
        setup();
        game = new Game(board, Player.BLUE, ruleBook);
        renderer.board(board);
        renderer.render();
        for(ActionUnion<GameActionHandler, ConsoleActionHandler> action : inputHandler) {
            try {
                action.firstHandler(game).secondHandler(this).execute();
                renderer.render();
            } catch (RuleViolation e) {
                output.println(e.getMessage());
            }
        }
    }

    public void execute(@NotNull Action<ConsoleActionHandler> action) throws RuleViolation {
        action.execute(this);
    }

    public void handle(@NotNull QuitAction action) {
        inputHandler.active(false);
    }

    public void handle(@NotNull HelpAction action) {
        for(Command command : inputHandler.commands()) {
            output.println(command.syntax());
        }
    }

}
