package machinestrike.game.machine;

import machinestrike.debug.Assert;
import machinestrike.game.*;
import machinestrike.game.level.Field;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

public class Gunner extends Machine{

    public Gunner(@NotNull MachineKey key, @NotNull Player player, int victoryPoints, int health, int strength,
                  int moveRange, int attackRange, @NotNull Orientation orientation, @NotNull Armor armor, @NotNull Set<Trait> traits) {
        super(key, player, victoryPoints, health, strength, moveRange, attackRange, orientation, armor, traits);
    }

    @Override
    public void attack() {
        Field field = Assert.requireNotNull(field());
        Game game = Assert.requireNotNull(field.board().game());
        game.performStandardAttack(this, field.position());
    }

    @Override
    public @NotNull List<Point> assailableFields(@NotNull Point from, @NotNull Orientation orientation) {
        return List.of(from.add(orientation.asPoint().multiply(attackRange())));
    }

    @Override
    public boolean canCurrentlyPerformAttack() {
        Field f = firstAssailableFieldWithMachine();
        if(f != null) {
            Assert.requireNotNull(f.machine());
            return f.machine().player() == player().opponent();
        }
        return false;
    }
}
