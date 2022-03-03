package machinestrike.game.machine.factory;

import machinestrike.game.Orientation;
import machinestrike.game.Player;
import machinestrike.game.machine.Armor;
import machinestrike.game.machine.Machine;
import machinestrike.game.machine.Melee;

import java.util.Set;

public class DefaultMachineFactory implements MachineFactory {

    private static DefaultMachineFactory instance;

    public static DefaultMachineFactory instance() {
        if(instance == null) {
            instance = new DefaultMachineFactory();
        }
        return instance;
    }

    private DefaultMachineFactory() {
    }

    public Machine createBurrower(Player player, Orientation orientation) {
        return new Melee("Burrower", player, 1, 4, 2, 2, 1, orientation, Armor.defaultArmor, Set.of(Machine.GROUNDED));
    }

    //TODO create factory methods for every missing machine

}
