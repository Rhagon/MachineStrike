package machinestrike.client.console.statemachine;

import machinestrike.client.console.action.MirrorTerrainAction;
import machinestrike.client.console.action.NewGameAction;
import machinestrike.client.console.action.PlaceMachineAction;
import machinestrike.client.console.action.SetTerrainAction;
import machinestrike.game.Point;
import machinestrike.game.level.Board;
import machinestrike.game.level.Terrain;
import machinestrike.game.machine.Machine;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SetupMode extends DefaultState {

    public SetupMode(@NotNull StateMachine stateMachine) {
        super(stateMachine);
    }

    @Override
    @NotNull
    public List<String> help() {
        List<String> h = new ArrayList<>();
        h.add("new game");
        h.add("change terrain at <position> to <type>");
        h.add("mirror terrain");
        h.addAll(super.help());
        return h;
    }

    @Override
    public void handle(@NotNull NewGameAction action) {
        stateMachine().client().newGame(action.boardSize());
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
        Machine machine = null;
        if(action.machineName() != null) {
            machine = stateMachine().client().machineFactory().forName(action.machineName(), action.player(), action.orientation());
            if(machine == null) {
                stateMachine().info("Available machines: ", stateMachine().client().machineFactory().names());
                return;
            }
        }
        if(!board.hasField(action.position())) {
            stateMachine().info("Invalid position");
            return;
        }
        board.field(action.position()).machine(machine);
        stateMachine().client().updateUI();
    }

}
