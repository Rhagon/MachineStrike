package machinestrike.game.machine;

import machinestrike.debug.Assert;
import machinestrike.game.Orientation;
import machinestrike.game.Player;
import machinestrike.game.Trait;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class Machine {

    public static final Trait GROUNDED = Trait.get("unit.grounded"),
        AIRBORNE = Trait.get("unit.airborne");

    @NotNull
    private final String name;
    @NotNull
    private final Player player;
    private final int victoryPoints;
    private int health, strength;
    private final int moveRange, attackRange;
    @NotNull
    private Orientation orientation;
    @NotNull
    private final Armor armor;
    @NotNull
    private final HashSet<Trait> traits;

    private boolean moved;
    private boolean attacked;
    private boolean overcharged;

    public Machine(@NotNull String name, @NotNull Player player, int victoryPoints, int health, int strength, int moveRange,
                   int attackRange, @NotNull Orientation orientation, @NotNull Armor armor, @NotNull Set<Trait> traits) {
        this.name = name;
        this.player = player;
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
        resetActions();
    }

    @NotNull
    public String name() {
        return name;
    }

    @NotNull
    public Player player() {
        return player;
    }

    public int victoryPoints() {
        return victoryPoints;
    }

    public int health() {
        return health;
    }

    @NotNull
    public Machine health(int health) {
        this.health = health;
        return this;
    }

    public int strength() {
        return strength;
    }

    @NotNull
    public Machine strength(int strength) {
        this.strength = strength;
        return this;
    }

    public int moveRange() {
        return moveRange;
    }

    public int attackRange() {
        return attackRange;
    }

    public abstract char descriptor();

    @NotNull
    public Orientation orientation() {
        return orientation;
    }

    @NotNull
    public Machine orientation(@NotNull Orientation orientation) {
        this.orientation = orientation;
        return this;
    }

    @NotNull
    public Armor armor() {
        return armor;
    }

    @NotNull
    public Machine addTrait(@NotNull Trait trait) {
        traits.add(trait);
        return this;
    }

    @NotNull
    public Machine removeTrait(@NotNull Trait trait) {
        traits.remove(trait);
        return this;
    }

    public boolean is(@NotNull Trait trait) {
        return traits.contains(trait);
    }

    @NotNull
    @UnmodifiableView
    public Set<Trait> traits() {
        return Collections.unmodifiableSet(traits);
    }

    @NotNull
    public abstract String typeName();

    public void resetActions() {
        moved = false;
        attacked = false;
        overcharged = false;
    }

    public boolean hasMoved() {
        return moved;
    }

    public boolean hasAttacked() {
        return attacked;
    }

    public boolean wasOvercharged() {
        return overcharged;
    }

    public void move() {
        moved = true;
    }

    public void attack() {
        attacked = true;
    }

    public void overcharge() {
        overcharged = true;
    }

    @Override
    public String toString() {
        return "[" + descriptor() + orientation.descriptor() + ']';
    }

}
