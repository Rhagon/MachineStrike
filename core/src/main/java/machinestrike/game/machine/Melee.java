package machinestrike.game.machine;

import machinestrike.debug.Assert;
import machinestrike.game.*;
import machinestrike.game.action.AttackAction;
import machinestrike.game.level.Board;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Melee extends Machine {

    public Melee(@NotNull String name, @NotNull Player player, int victoryPoints, int health, int strength,
                 int moveRange, int attackRange, @NotNull Orientation orientation, @NotNull Armor armor, Set<Trait> traits) {
        super(name, player, victoryPoints, health, strength, moveRange, attackRange, orientation, armor, traits);
    }

    @Override
    @Contract(pure = true)
    public char descriptor() {
        return player() == Player.BLUE ? 'M' : 'm';
    }

    @Override
    @Contract(pure = true)
    public @NotNull String typeName() {
        return "Melee";
    }

    @Override
    public void attack(@NotNull AttackAction action) {
        Assert.requireNotNull(field());
        Game game = field().board().game();
        Assert.requireNotNull(game);
        Assert.equal(field().position(), action.origin());
        for(Point point : assailableFields()) {
            Machine machineOnPoint = game.board().field(point).machine();
            if(machineOnPoint != null && machineOnPoint.player() == player().opponent()) {
                int damage = Math.max(1, calculateCombatPower(orientation()) - machineOnPoint.calculateCombatPower(orientation().add(Orientation.SOUTH)));
                if(damage == 1) {
                    //TODO implement defense break
                }
                machineOnPoint.damage(damage);
                if(damage == 1) {
                    damage(1);
                }
            }
        }
        game.usedMachine(this);
        if(!canAttack()) {
            overcharge();
        }
        canAttack(false);
    }

    @Override
    @NotNull
    @Contract(pure = true)
    public List<Point> assailableFields(@NotNull Point from, @NotNull Orientation orientation) {
        Assert.requireNotNull(field());
        Board board = field().board();
        List<Point> fields = new ArrayList<>();
        Point current = from;
        for(int i = 0; i < attackRange(); ++i) {
            current = current.add(orientation.asPoint());
            if(board.hasField(current)) {
                fields.add(current);
            }
        }
        return fields;
    }
}
