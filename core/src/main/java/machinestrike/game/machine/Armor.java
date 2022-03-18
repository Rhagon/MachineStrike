package machinestrike.game.machine;

import machinestrike.util.Orientation;
import org.jetbrains.annotations.NotNull;

public record Armor(@NotNull Type front, @NotNull Type left, @NotNull Type right, @NotNull Type back) {

    public static final Armor defaultArmor = new Armor(Type.ARMORED, Type.NORMAL, Type.NORMAL, Type.EXPOSED);

    public enum Type {
        ARMORED(1),
        NORMAL(0),
        EXPOSED(-1);

        private final int damageModifier;

        Type(int damageModifier) {
            this.damageModifier = damageModifier;
        }

        public int damageModifier() {
            return damageModifier;
        }
    }

    @NotNull
    public Type inDirection(Orientation orientation) {
        return switch(orientation) {
            case NORTH -> front;
            case EAST -> right;
            case SOUTH -> back;
            case WEST -> left;
        };
    }

}
