package machinestrike.game.machine.factory;

import machinestrike.game.Orientation;
import machinestrike.game.Player;
import machinestrike.game.machine.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

import static machinestrike.game.machine.Machines.*;

public class DefaultMachineFactory implements MachineFactory {

    private static DefaultMachineFactory instance;

    public static DefaultMachineFactory instance() {
        if(instance == null) {
            instance = new DefaultMachineFactory();
        }
        return instance;
    }

    protected interface Generator {
        @NotNull Machine generate(@NotNull Player player, @NotNull Orientation orientation);
    }

    protected void addGenerator(Generator generator) {
        Machine generated = generator.generate(Player.BLUE, Orientation.NORTH);
        gens.put(generated.key(), generator);
        keys.add(generated.key());
    }

    private void addGenerators() {
        addGenerator(this::createBurrower);
        addGenerator(this::createScrapper);
    }

    @NotNull
    private final HashMap<MachineKey, Generator> gens;
    @NotNull
    private final List<MachineKey> keys;

    private DefaultMachineFactory() {
        gens = new HashMap<>();
        keys = new ArrayList<>();
        addGenerators();
    }

    @Override
    @Nullable
    public Machine create(@NotNull MachineKey key, @NotNull Player player, @NotNull Orientation orientation) {
        Generator gen = gens.get(key);
        if(gen == null) {
            return null;
        }
        return gen.generate(player, orientation);
    }

    @Override
    public @NotNull List<MachineKey> keys() {
        return Collections.unmodifiableList(keys);
    }

    @NotNull
    public Machine createBurrower(@NotNull Player player, @NotNull Orientation orientation) {
        return new Melee(BURROWER, player, 1, 4, 2, 2, 1, orientation, Armor.defaultArmor, Set.of(Machine.GROUNDED));
    }

    @NotNull
    public Machine createScrapper(@NotNull Player player, @NotNull Orientation orientation) {
        return new Gunner(SCRAPPER, player, 1, 4, 2, 2, 2, orientation, Armor.defaultArmor, Set.of(Machine.GROUNDED));
    }

    //TODO create factory methods for every missing machine

}
