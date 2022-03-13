package machinestrike.client.console.renderer.component;

import machinestrike.client.console.renderer.Anchor;
import machinestrike.client.console.renderer.color.ColorKey;
import machinestrike.client.console.renderer.color.Colors;
import machinestrike.debug.Assert;
import machinestrike.game.Game;
import machinestrike.game.Orientation;
import machinestrike.game.level.Field;
import machinestrike.game.machine.Armor;
import machinestrike.game.machine.Machine;
import org.jetbrains.annotations.NotNull;

public class FieldBox extends BoxPanel {

    @NotNull
    private final Field field;
    private final Label machine, strength, move, health;
    @NotNull
    private final Label[] armorLabels;

    public FieldBox(@NotNull Field field) {
        super(new Outline('-', '|', '+', 1, 1));
        this.field = field;
        machine = new Label();
        machine.alignment(Label.Alignment.TOP_CENTER);
        machine.anchor(Anchor.TOP_EDGE.size(0, 1).position(0, 1).pad(0, 1, 1, 0));
        add(machine);
        strength = new Label();
        strength.alignment(Label.Alignment.BOTTOM_LEFT);
        strength.anchor(Anchor.BOTTOM_LEFT.size(3, 1).position(1, -1));
        add(strength);
        move = new Label();
        move.alignment(Label.Alignment.BOTTOM_CENTER);
        move.anchor(Anchor.BOTTOM_EDGE.size(0, 1).position(0, -1).pad(0, 4, 4, 0));
        add(move);
        health = new Label();
        health.alignment(Label.Alignment.BOTTOM_RIGHT);
        health.anchor(Anchor.BOTTOM_RIGHT.size(3, 1).position(-1, -1));
        Panel armorPanel = new Panel();
        armorPanel.anchor(Anchor.CENTER.size(4, 1));
        armorLabels = new Label[Orientation.values().length];
        for(int i = 0; i < Orientation.values().length; ++i) {
            armorLabels[i] = new Label();
        }
        armorLabels[Orientation.WEST.index()].anchor(Anchor.CENTER.size(1, 1).position(-1, 0));
        armorLabels[Orientation.NORTH.index()].anchor(Anchor.CENTER.size(1, 1).position(0, 0));
        armorLabels[Orientation.SOUTH.index()].anchor(Anchor.CENTER.size(1, 1).position(1, 0));
        armorLabels[Orientation.EAST.index()].anchor(Anchor.CENTER.size(1, 1).position(2, 0));
        for(Orientation orientation : Orientation.values()) {
            armorLabels[orientation.index()].text("");
            armorLabels[orientation.index()].color(Colors.LABEL);
            add(armorLabels[orientation.index()]);
        }
        add(health);
    }

    @NotNull
    public Field field() {
        return field;
    }

    public void update() {
        Machine m = field.machine();
        outline(new Outline('-', '|', '+', 1, 1, ColorKey.get("terrain." + field().terrain().name().toLowerCase())));
        if(m != null) {
            machine.text(m.name());
            machine.color(ColorKey.get("player." + m.player().toString().toLowerCase()));
            Game game = field().board().game();
            Assert.requireNotNull(game);
            strength.text("\u2694" + game.ruleBook().calculateStrength(m, m.orientation(), false));
            move.text("\uD83D\uDC5E" + m.moveRange() + m.orientation().descriptor());
            health.text("\u2665" + m.health());
            for(Orientation orientation : Orientation.values()) {
                armorLabels[orientation.index()].text(armorSymbol(orientation) + "");
                Armor.Type armor = m.armor().inDirection(orientation.subtract(m.orientation()));
                ColorKey color = switch(armor) {
                    case ARMORED -> Colors.ARMORED;
                    case EXPOSED -> Colors.EXPOSED;
                    default -> Colors.NORMAL_ARMOR;
                };
                armorLabels[orientation.index()].color(color);
            }
        } else {
            machine.text("");
            strength.text("");
            move.text("");
            health.text("");
            for(Orientation orientation : Orientation.values()) {
                armorLabels[orientation.index()].text("");
                armorLabels[orientation.index()].color(Colors.LABEL);
            }
        }
        repaint();
    }

    private static char armorSymbol(Orientation orientation) {
        return switch(orientation) {
            case NORTH -> '\u02C4';
            case EAST -> '>';
            case WEST -> '<';
            case SOUTH -> '\u02C5';
        };
    }

}
