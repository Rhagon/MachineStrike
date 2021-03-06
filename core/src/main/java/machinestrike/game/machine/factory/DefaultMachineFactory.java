package machinestrike.game.machine.factory;

import machinestrike.game.Orientation;
import machinestrike.game.Player;
import machinestrike.game.machine.Armor;
import machinestrike.game.machine.Gunner;
import machinestrike.game.machine.Machine;
import machinestrike.game.machine.Melee;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

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
        gens.put(generated.name().toLowerCase(), generator);
        names.add(generated.name().toLowerCase());
    }

    private void addGenerators() {
        addGenerator(this::createBurrower);
        addGenerator(this::createScrapper);
    }

    @NotNull
    private final HashMap<String, Generator> gens;
    @NotNull
    private final List<String> names;

    private DefaultMachineFactory() {
        gens = new HashMap<>();
        names = new ArrayList<>();
        addGenerators();
    }

    @Override
    @Nullable
    public Machine forName(@NotNull String name, @NotNull Player player, @NotNull Orientation orientation) {
        Generator gen = gens.get(name.toLowerCase());
        if(gen == null) {
            return null;
        }
        return gen.generate(player, orientation);
    }

    @Override
    public @NotNull List<String> names() {
        return Collections.unmodifiableList(names);
    }

    @NotNull
    public Machine createBurrower(@NotNull Player player, @NotNull Orientation orientation) {
        return new Melee("Burrower", player, 1, 4, 2, 2, 1, orientation, Armor.defaultArmor, Set.of(Machine.GROUNDED));
    }

    @NotNull
    public Machine createScrapper(@NotNull Player player, @NotNull Orientation orientation) {
        return new Gunner("Scrapper", player, 1, 4, 2, 2, 2, orientation, Armor.defaultArmor, Set.of(Machine.GROUNDED));
    }

    //TODO create factory methods for every missing machine

}
