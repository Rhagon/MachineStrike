package machinestrike.client.console;

import machinestrike.action.Action;
import machinestrike.client.console.action.ClientActionHandler;
import machinestrike.client.console.action.HelpAction;
import machinestrike.client.console.action.QuitAction;
import machinestrike.client.console.action.RedrawAction;
import machinestrike.client.console.input.Command;
import machinestrike.client.console.input.InputHandler;
import machinestrike.client.console.input.factory.CommandListFactory;
import machinestrike.client.console.input.factory.DefaultCommandListFactory;
import machinestrike.client.console.renderer.DefaultFieldFormatter;
import machinestrike.client.console.renderer.FieldFormatter;
import machinestrike.client.console.renderer.component.Anchor;
import machinestrike.client.console.renderer.component.BoardBox;
import machinestrike.client.console.renderer.component.Canvas;
import machinestrike.debug.Assert;
import machinestrike.game.Game;
import machinestrike.game.Orientation;
import machinestrike.game.Player;
import machinestrike.game.action.AttackAction;
import machinestrike.game.action.MoveAction;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;
import java.io.PrintStream;

public class ConsoleClient implements ClientActionHandler {

    public static void main(String[] args) {
        new ConsoleClient().run();
    }

    @NotNull
    private PrintStream output;
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
    @Nullable
    private Board board;
    @Nullable
    private Game game;
    @NotNull
    private Canvas canvas;
    @NotNull
    private BoardBox boardBox;

    public ConsoleClient() {
        this(System.in, System.out);
    }

    public ConsoleClient(@NotNull InputStream input, @NotNull PrintStream output) {
        this(input, output, DefaultCommandListFactory.instance(),
                DefaultBoardFactory.instance(), DefaultMachineFactory.instance(), DefaultTerrainFactory.instance(),
                DefaultRuleBookFactory.instance(), DefaultFieldFormatter.instance());
    }

    public ConsoleClient(@NotNull InputStream input, @NotNull PrintStream output,
                         @NotNull CommandListFactory commandFactory, @NotNull BoardFactory bf, @NotNull MachineFactory mf,
                         @NotNull TerrainFactory tf, @NotNull RuleBookFactory rf, @NotNull FieldFormatter formatter) {
        this.output = output;
        this.inputHandler = new InputHandler(input, output, commandFactory.createCommandList());
        this.boardFactory = bf;
        this.machineFactory = mf;
        this.terrainFactory = tf;
        this.ruleBookFactory = rf;
        this.board = null;
        this.game = null;
        this.canvas = new Canvas(100, 40);
        this.boardBox = new BoardBox(board);
        this.boardBox.anchor(Anchor.AREA);
        canvas.stage(boardBox);
    }

    @NotNull
    public Canvas canvas() {
        return canvas;
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
        boardBox.board(board);
        RuleBook ruleBook = ruleBookFactory.createRuleBook();
        setup();
        game = new Game(board, Player.BLUE, ruleBook);

        render();
        for(Action<? super ClientActionHandler> action : inputHandler) {
            try {
                action.execute(this);
                render();
            } catch (RuleViolation e) {
                output.println(e.getMessage());
            }
        }
    }

    public void update() {
        canvas.ignoreRepaint(true);
        boardBox.update();
        canvas.ignoreRepaint(false);
    }

    private void render() {
        update();
        canvas.repaint();
        clearConsole();
        output.println(canvas);
    }

    private void clearConsole() {
        output.println("\033[H\033[2J");
    }

    public void handle(@NotNull QuitAction action) {
        inputHandler.active(false);
    }

    public void handle(@NotNull HelpAction action) {
        for(Command<?> command : inputHandler.commands()) {
            output.println(command.syntax());
        }
    }

    public void handle(@NotNull RedrawAction action) {
        render();
    }

    public void handle(@NotNull AttackAction action) throws RuleViolation {
        Assert.requireNotNull(game);
        game.handle(action);
        render();
    }

    public void handle(@NotNull MoveAction action) throws RuleViolation {
        Assert.requireNotNull(game);
        game.handle(action);
        render();
    }

}
