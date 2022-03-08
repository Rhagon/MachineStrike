package machinestrike.client.console;

import machinestrike.action.Action;
import machinestrike.client.console.action.*;
import machinestrike.client.console.input.Command;
import machinestrike.client.console.input.InputHandler;
import machinestrike.client.console.input.factory.CommandListFactory;
import machinestrike.client.console.input.factory.DefaultCommandListFactory;
import machinestrike.client.console.renderer.DefaultFieldFormatter;
import machinestrike.client.console.renderer.FieldFormatter;
import machinestrike.client.console.renderer.component.*;
import machinestrike.debug.Assert;
import machinestrike.game.Game;
import machinestrike.game.Orientation;
import machinestrike.game.Player;
import machinestrike.game.Point;
import machinestrike.game.action.AttackAction;
import machinestrike.game.action.MoveAction;
import machinestrike.game.level.Board;
import machinestrike.game.level.factory.BoardFactory;
import machinestrike.game.level.factory.DefaultBoardFactory;
import machinestrike.game.level.factory.DefaultTerrainFactory;
import machinestrike.game.level.factory.TerrainFactory;
import machinestrike.game.machine.factory.DefaultMachineFactory;
import machinestrike.game.machine.factory.MachineFactory;
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
    @Nullable
    private Board board;
    @Nullable
    private Game game;
    @NotNull
    private final Canvas canvas;
    @NotNull
    private final BoardBox boardBox;
    @NotNull
    private final Label infoText;

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
        this.inputHandler = new InputHandler(input, commandFactory.createCommandList());
        this.boardFactory = bf;
        this.machineFactory = mf;
        this.terrainFactory = tf;
        this.ruleBookFactory = rf;
        this.board = null;
        this.game = null;
        this.canvas = new Canvas(150, 42);
        @NotNull Panel scene = new Panel();
        scene.anchor(Anchor.AREA);
        this.boardBox = new BoardBox(board, 2.2f);
        this.boardBox.anchor(Anchor.AREA.pad(0, 0, 60, 0));
        @NotNull BoxPanel infoPanel = new BoxPanel(new BoxPanel.Outline('-', '|', '+', 1, 1));
        infoPanel.anchor(Anchor.TOP_RIGHT.size(60, 15));
        infoText = new Label();
        infoText.anchor(Anchor.AREA.pad(1, 1, 1, 1));
        infoPanel.add(infoText);
        scene.add(this.boardBox);
        scene.add(infoPanel);
        this.canvas.child(scene);
    }

    @NotNull
    public Canvas canvas() {
        return canvas;
    }

    public void game(@Nullable Game game) {
        this.game = game;
        if(game != null) {
            board(game.board());
        }
    }

    public void board(@Nullable Board board) {
        this.board = board;
        canvas.ignoreRepaint(() -> boardBox.board(board));
    }

    public void setup(@NotNull Board b) {
        b.field(1, 2).machine(machineFactory.createBurrower(Player.BLUE, Orientation.NORTH));
        for(int i = 0; i <= 3; ++i) {
            b.field(3, i).terrain(terrainFactory.createChasm());
            b.field(i, 3).terrain(terrainFactory.createMarsh());
        }
        game(new Game(b, Player.BLUE, ruleBookFactory.createRuleBook()));
    }

    public void run() {
        Board b = boardFactory.createStandardBoard(terrainFactory);
        setup(b);
        forceRedraw();
        output.print("> ");
        for(Action<? super ClientActionHandler> action : inputHandler) {
            try {
                action.execute(this);
            } catch (RuleViolation e) {
                output.println(e.getMessage());
            }
            if(inputHandler.active()) {
                output.print("> ");
            }
        }
    }

    public void update() {
        canvas.ignoreRepaint(boardBox::update);
    }

    public void updateWindowSize(int width, int height) {
        width = Math.max(width, 0);
        height = Math.max(height, 0);
        canvas.size(new Point(width, height));
    }

    private void forceRedraw() {
        update();
        canvas.repaint();
        clearConsole();
        output.println(canvas);
    }

    private void render() {
        update();
        clearConsole();
        output.println(canvas);
    }

    private void clearConsole() {
        output.println("\033[H\033[2J");
    }

    @Override
    public void handle(@NotNull QuitAction action) {
        inputHandler.active(false);
    }

    @Override
    public void handle(@NotNull HelpAction action) {
        StringBuilder builder = new StringBuilder();
        for(Command<?> command : inputHandler.commands()) {
            builder.append(command.syntax()).append("\n");
        }
        infoText.text(builder.toString());
        render();
    }

    @Override
    public void handle(@NotNull RedrawAction action) {
        forceRedraw();
    }

    @Override
    public void handle(@NotNull AttackAction action) throws RuleViolation {
        Assert.requireNotNull(game);
        game.handle(action);
        render();
    }

    @Override
    public void handle(@NotNull MoveAction action) throws RuleViolation {
        Assert.requireNotNull(game);
        game.handle(action);
        render();
    }

    @Override
    public void handle(@NotNull SetWindowSizeAction action) {
        updateWindowSize(action.width(), action.height());
        infoText.text("Changed window size to " + new Point(action.width(), action.height()));
        render();
    }

    @Override
    public void handle(@NotNull UnknownCommandAction action) {
        infoText.text("Unknown command");
        render();
    }

}
