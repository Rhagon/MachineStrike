package machinestrike.client.console;

import machinestrike.client.console.renderer.DefaultFieldFormatter;
import machinestrike.client.console.renderer.FieldFormatter;
import machinestrike.client.console.renderer.component.*;
import machinestrike.client.console.statemachine.client.ClientStateMachine;
import machinestrike.game.Game;
import machinestrike.game.Player;
import machinestrike.game.Point;
import machinestrike.game.level.factory.BoardFactory;
import machinestrike.game.level.factory.DefaultBoardFactory;
import machinestrike.game.level.factory.DefaultTerrainFactory;
import machinestrike.game.level.factory.TerrainFactory;
import machinestrike.game.machine.factory.DefaultMachineFactory;
import machinestrike.game.machine.factory.MachineFactory;
import machinestrike.game.rule.factory.DefaultRuleBookFactory;
import machinestrike.game.rule.factory.RuleBookFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;
import java.io.PrintStream;

public class ConsoleClient {

    public static void main(String[] args) {
        new ConsoleClient(new Point(175, 45)).run();
    }

    @NotNull
    private final PrintStream output;
    @NotNull
    private final InputStream input;
    @NotNull
    private final BoardFactory boardFactory;
    @NotNull
    private final MachineFactory machineFactory;
    @NotNull
    private final TerrainFactory terrainFactory;
    @NotNull
    private final RuleBookFactory ruleBookFactory;
    @NotNull
    private Game game;
    @NotNull
    private final ClientStateMachine stateMachine;
    @NotNull
    private final Canvas canvas;
    @NotNull
    private final BoardBox boardBox;
    @NotNull
    private final Label infoText;

    public ConsoleClient(@NotNull Point windowSize) {
        this(windowSize, System.in, System.out);
    }

    public ConsoleClient(@NotNull Point windowSize, @NotNull InputStream input, @NotNull PrintStream output) {
        this(windowSize, input, output, DefaultBoardFactory.instance(), DefaultMachineFactory.instance(),
                DefaultTerrainFactory.instance(), DefaultRuleBookFactory.instance(), DefaultFieldFormatter.instance());
    }

    public ConsoleClient(@NotNull Point windowSize, @NotNull InputStream input, @NotNull PrintStream output,
                         @NotNull BoardFactory bf, @NotNull MachineFactory mf, @NotNull TerrainFactory tf,
                         @NotNull RuleBookFactory rf, @NotNull FieldFormatter formatter) {
        this.output = output;
        this.input = input;
        this.boardFactory = bf;
        this.machineFactory = mf;
        this.terrainFactory = tf;
        this.ruleBookFactory = rf;
        this.game = createNewGame();
        this.stateMachine = new ClientStateMachine(this);
        this.canvas = new Canvas(windowSize, DefaultColorSchemeFactory.instance().create());
        this.boardBox = new BoardBox(game.board(), 2.4f);
        this.infoText = new Label();
        setupUI();
    }

    @NotNull
    public Canvas canvas() {
        return canvas;
    }

    public void game(@NotNull Game game) {
        this.game = game;
        canvas.ignoreRepaint(() -> boardBox.board(game.board()));
        updateUI();
    }

    @NotNull
    public Game game() {
        return game;
    }

    @NotNull
    public TerrainFactory terrainFactory() {
        return terrainFactory;
    }

    @NotNull
    public MachineFactory machineFactory() {
        return machineFactory;
    }

    @NotNull
    public InputStream input() {
        return input;
    }

    public void run() {
        updateUI();
        render();
        stateMachine.run();
    }

    public void updateUI() {
        canvas.ignoreRepaint(boardBox::update);
        canvas.repaint();
    }

    public void updateWindowSize(int width, int height) {
        width = Math.max(width, 0);
        height = Math.max(height, 0);
        canvas.size(new Point(width, height));
    }

    public void render() {
        clearConsole();
        output.println(canvas);
    }

    public Game createNewGame() {
        return new Game(boardFactory.createStandardBoard(terrainFactory), Player.BLUE, ruleBookFactory.createRuleBook());
    }

    public Game createNewGame(@NotNull Point boardSize) {
        return new Game(boardFactory.createStandardBoard(boardSize, terrainFactory), Player.BLUE, ruleBookFactory.createRuleBook());
    }

    public void newGame(@Nullable Point boardSize) {
        game(boardSize == null ? createNewGame() : createNewGame(boardSize));
    }

    public void info(@NotNull String message) {
        infoText.text(message);
    }

    protected void setupUI() {
        Panel scene = new Panel();
        scene.anchor(Anchor.AREA);
        boardBox.anchor(Anchor.AREA.pad(0, 0, 60, 0));
        BoxPanel infoPanel = new BoxPanel(new BoxPanel.Outline('-', '|', '+', 1, 1));
        infoPanel.anchor(Anchor.BOTTOM_RIGHT.size(60, 15));
        infoText.anchor(Anchor.AREA.pad(1, 1, 1, 1));
        infoPanel.add(infoText);
        scene.add(boardBox);
        scene.add(infoPanel);
        canvas.child(scene);
    }

    protected void clearConsole() {
        output.println("\033[H\033[2J"); //Does this work on linux and macos as well?
    }

}
