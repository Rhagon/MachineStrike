package machinestrike.game.machine;

import machinestrike.debug.Assert;
import machinestrike.game.*;
import machinestrike.game.action.AttackAction;
import machinestrike.game.action.MoveAction;
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
        AIRBORNE = Trait.get("unit.airborne");

    @NotNull
    private final String name;
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

    private boolean canMove;
    private boolean canAttack;
    private boolean overcharged;

    public Machine(@NotNull String name, @NotNull Player player, int victoryPoints, int health, int strength, int moveRange,
                   int attackRange, @NotNull Orientation orientation, @NotNull Armor armor, @NotNull Set<Trait> traits) {
        this.name = name;
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
        resetActions();
    }

    @NotNull
    @Contract(pure = true)
    public String name() {
        return name;
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

    @Contract(pure = true)
    public abstract char descriptor();

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

    @NotNull
    @Contract(pure = true)
    public abstract String typeName();

    public void resetActions() {
        canMove = true;
        canAttack = true;
        overcharged = false;
    }

    @Contract(pure = true)
    public boolean canMove() {
        return canMove;
    }

    @Contract("_ -> this")
    protected Machine canMove(boolean canMove) {
        this.canMove = canMove;
        return this;
    }

    @Contract(pure = true)
    public boolean canAttack() {
        return canAttack;
    }

    @Contract("_ -> this")
    protected Machine canAttack(boolean canAttack) {
        this.canAttack = canAttack;
        return this;
    }

    @Contract(pure = true)
    public boolean wasOvercharged() {
        return overcharged;
    }

    @Contract("_ -> this")
    protected Machine wasOvercharged(boolean overcharged) {
        this.overcharged = overcharged;
        return this;
    }

    /**
     * Executes a move without verifying it. That has to be done by the caller.
     * @param action The move that is executed
     */
    public void move(@NotNull MoveAction action) {
        Assert.requireNotNull(field);
        Game game = field.board().game();
        Assert.requireNotNull(game);
        Assert.equal(field.position(), action.origin());
        if(!canMove) {
            overcharged = true;
        }
        canMove = false;
        if(action.sprint()) {
            canAttack = false;
        }
        field(game.board().field(action.destination()));
        orientation(action.orientation());
        game.usedMachine(this);
    }

    public abstract void attack(@NotNull AttackAction action);

    @Contract(pure = true)
    @NotNull
    public List<Point> attackableFields() {
        Assert.requireNotNull(field);
        return attackableFields(field.position(), orientation);
    }

    @Contract(pure = true)
    @NotNull
    public abstract List<Point> attackableFields(@NotNull Point from, @NotNull Orientation orientation);

    public void damage(int amount) {
        Assert.requireNotNull(field);
        Game game = field.board().game();
        Assert.requireNotNull(game);
        health -= amount;
        if(health <= 0) {
            field(null);
            game.addVictoryPoints(player.opponent(), victoryPoints);
        }
    }

    @Override
    public String toString() {
        return "[" + descriptor() + orientation.descriptor() + ']';
    }

}
