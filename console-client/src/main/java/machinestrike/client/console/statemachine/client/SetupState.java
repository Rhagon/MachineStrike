package machinestrike.client.console.statemachine.client;

import machinestrike.action.Action;
import machinestrike.action.ActionExecutionFailure;
import machinestrike.client.console.action.client.NewGameAction;
import machinestrike.client.console.action.client.QuitAction;
import machinestrike.client.console.action.setup.*;
import machinestrike.client.console.input.Command;
import machinestrike.client.console.input.setup.ClientSetupCommandFactory;
import machinestrike.game.Point;
import machinestrike.game.level.Board;
import machinestrike.game.level.Terrain;
import machinestrike.game.machine.Machine;
import machinestrike.game.machine.factory.MachineFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.ArrayList;
import java.util.List;

public class SetupState extends ClientInputState<ClientSetupActionHandler> implements ClientSetupActionHandler {

    @NotNull
    @UnmodifiableView
    private final List<Command<? super ClientSetupActionHandler>> commands;

    public SetupState(@NotNull ClientStateMachine stateMachine) {
        super(stateMachine);
        this.commands = ClientSetupCommandFactory.instance().create();
    }

    @Override
    protected List<Command<? super ClientSetupActionHandler>> commands() {
        return commands;
    }

    @Override
    protected void execute(@NotNull Action<? super ClientSetupActionHandler> action) throws ActionExecutionFailure {
        action.execute(this);
    }

    @Override
    public void handle(@NotNull NewGameAction action) {
        stateMachine().client().newGame(action.boardSize());
    }

    @Override
    public void handle(@NotNull QuitAction action) {
        stop(new ClientExitState(stateMachine()));
    }

    @Override
    public void handle(@NotNull MirrorTerrainAction action) {
        Board board = stateMachine().client().game().board();
        Point topRightCorner = new Point(board.sizeX() - 1, board.sizeY() - 1);
        for(int r = 0; r < board.sizeY() / 2; ++r) {
            for(int c = 0; c < board.sizeX(); ++c) {
                Point p = new Point(c, r);
                board.field(topRightCorner.subtract(p)).terrain(board.field(p).terrain());
            }
        }
        stateMachine().client().updateUI();
    }

    @Override
    public void handle(@NotNull PlaceMachineAction action) {
        Board board = stateMachine().client().game().board();
        MachineFactory factory = stateMachine().client().machineFactory();
        Machine machine = factory.create(action.machineKey(), action.player(), action.orientation());
        if(machine == null) {
            List<String> names = new ArrayList<>();
            factory.keys().forEach(key -> names.add(key.name()));
            stateMachine().info("Available machines:", names);
            return;
        }
        if(!board.hasField(action.position())) {
            stateMachine().info("Invalid position");
            return;
        }
        board.field(action.position()).machine(machine);
        stateMachine().client().updateUI();
    }

    @Override
    public void handle(@NotNull SetTerrainAction action) {
        Board board = stateMachine().client().game().board();
        Terrain terrain = stateMachine().client().terrainFactory().forName(action.terrainName());
        if(terrain == null) {
            List<String> info = new ArrayList<>();
            info.add("Available Terrains:");
            info.addAll(stateMachine().client().terrainFactory().names());
            stateMachine().info(info);
            return;
        }
        if(!board.hasField(action.position())) {
            stateMachine().info("Invalid postion");
            return;
        }
        board.field(action.position()).terrain(terrain);
        stateMachine().client().updateUI();
    }

    @Override
    public void handle(@NotNull StartGameAction action) {
        stop(new GameState(stateMachine()));
    }

}
