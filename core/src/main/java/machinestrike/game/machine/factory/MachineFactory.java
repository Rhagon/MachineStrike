package machinestrike.game.machine.factory;

import machinestrike.game.Orientation;
import machinestrike.game.Player;
import machinestrike.game.machine.Machine;

public interface MachineFactory {
    Machine createBurrower(Player blue, Orientation north);
}
