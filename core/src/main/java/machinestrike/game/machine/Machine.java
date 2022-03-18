package machinestrike.game.machine;

import machinestrike.debug.Assert;
import machinestrike.util.Orientation;
import machinestrike.game.Player;
import machinestrike.util.Point;
import machinestrike.game.Trait;
import machinestrike.game.level.Field;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Machine {

    public static final Trait GROUNDED = Trait.get("unit.grounded"),
            AIRBORNE = Trait.get("unit.airborne"),
            IGNORE_MOVE_IMPEDIMENT = Trait.get("unit.ignore-move-impediment"),
            IGNORE_TERRAIN_MODIFIER = Trait.get("unit.ignore-terrain-modifier")
    ;

    @NotNull
    private final MachineKey key;
    @NotNull
    private final Player player;
    @Nullable
    private Field field;
    private final int victoryPoints;
    private int health, strength;
    private final int moveRange, attackRange;
    @NotNull
    private Orientation orientation;
    @NotNull
    private final Armor armor;
    @NotNull
    private final HashSet<Trait> traits;

    public Machine(@NotNull MachineKey key, @NotNull Player player, int victoryPoints, int health, int strength, int moveRange,
                   int attackRange, @NotNull Orientation orientation, @NotNull Armor armor, @NotNull Set<Trait> traits) {
        this.key = key;
        this.player = player;
        this.field = null;
        this.victoryPoints = victoryPoints;
        this.health = health;
        this.moveRange = moveRange;
        this.attackRange = attackRange;
        this.strength = strength;
        this.orientation = orientation;
        this.armor = armor;
        this.traits = new HashSet<>(traits);
        Assert.lessOrEqual(0, victoryPoints);
        Assert.less(0, health);
        Assert.less(0, strength);
    }

    @NotNull
    @Contract(pure = true)
    public MachineKey key() {
        return key;
    }

    @NotNull
    @Contract(pure = true)
    public Player player() {
        return player;
    }

    @Nullable
    @Contract(pure = true)
    public Field field() {
        return field;
    }

    /**
     * Moves the machine to a different field. Destination can be a field on a different board.
     * Should there already be a machine at the destination, it will be removed from that field.
     * @param field The destination for the machine.
     * @return self
     */
    @Contract(value = "_ -> this")
    public Machine field(@Nullable Field field) {
        if(this.field == field) {
            return this;
        }
        Field oldField = this.field;
        this.field = field;
        if(oldField != null) {
            oldField.machine(null);
        }
        if(field != null) {
            field.machine(this);
        }
        return this;
    }

    @Contract(pure = true)
    public int victoryPoints() {
        return victoryPoints;
    }

    @Contract(pure = true)
    public int health() {
        return health;
    }

    @NotNull
    @Contract("_ -> this")
    public Machine health(int health) {
        this.health = health;
        return this;
    }

    @Contract(pure = true)
    public int strength() {
        return strength;
    }

    @NotNull
    @Contract("_ -> this")
    public Machine strength(int strength) {
        this.strength = strength;
        return this;
    }

    @Contract(pure = true)
    public int moveRange() {
        return moveRange;
    }

    @Contract(pure = true)
    public int attackRange() {
        return attackRange;
    }

    @NotNull
    @Contract(pure = true)
    public Orientation orientation() {
        return orientation;
    }

    @NotNull
    @Contract("_ -> this")
    public Machine orientation(@NotNull Orientation orientation) {
        this.orientation = orientation;
        return this;
    }

    @NotNull
    @Contract(pure = true)
    public Armor armor() {
        return armor;
    }

    @NotNull
    @Contract("_ -> this")
    public Machine addTrait(@NotNull Trait trait) {
        traits.add(trait);
        return this;
    }

    @NotNull
    @Contract("_ -> this")
    public Machine removeTrait(@NotNull Trait trait) {
        traits.remove(trait);
        return this;
    }

    @Contract(pure = true)
    public boolean is(@NotNull Trait trait) {
        return traits.contains(trait);
    }

    @NotNull
    @UnmodifiableView
    @Contract(pure = true)
    public Set<Trait> traits() {
        return Collections.unmodifiableSet(traits);
    }

    /**
     *
     */
    @Contract(pure = true)
    @Nullable
    public Field firstAssailableFieldWithMachine() {
        Assert.requireNotNull(field);
        for(Point point : assailableFields(field.position(), orientation)) {
            Machine machineOnPoint = field.board().field(point).machine();
            if(machineOnPoint != null) {
                return field.board().field(point);
            }
        }
        return null;
    }

    /**
     * Executes an attack without verifying it. That has to be done by the caller.
     */
    public abstract void attack();

    /**
     * Creates a list of all possible positions that the machine can attack on relative to the passed point and orientation
     * @param from Relative point
     * @param orientation Relative position
     */
    @Contract(pure = true)
    @NotNull
    public abstract List<Point> assailableFields(@NotNull Point from, @NotNull Orientation orientation);

    /**
     *
     */
    public abstract boolean canCurrentlyPerformAttack();



}
