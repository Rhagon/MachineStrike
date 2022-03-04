package machinestrike.game.machine;

import machinestrike.debug.Assert;
import machinestrike.game.Orientation;
import machinestrike.game.Player;
import machinestrike.game.Point;
import machinestrike.game.Trait;
import machinestrike.game.action.AttackAction;
import machinestrike.game.level.Field;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

public class Gunner extends Machine{

    public Gunner(@NotNull String name, @NotNull Player player, int victoryPoints, int health, int strength,
                  int moveRange, int attackRange, @NotNull Orientation orientation, @NotNull Armor armor, @NotNull Set<Trait> traits) {
        super(name, player, victoryPoints, health, strength, moveRange, attackRange, orientation, armor, traits);
    }

    @Override
    public char descriptor() {
        return player() == Player.BLUE ? 'G' : 'g';
    }

    @Override
    public @NotNull String typeName() {
        return "Gunner";
    }

    @Override
    public void attack(@NotNull AttackAction<?> action) {
        performStandardAttack(this, action.origin());
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
