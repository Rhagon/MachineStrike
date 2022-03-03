package machinestrike.client.console.renderer;

import machinestrike.game.level.Field;
import machinestrike.game.machine.Machine;

import java.util.ArrayList;
import java.util.List;

public class DefaultFieldFormatter implements FieldFormatter {

    private static DefaultFieldFormatter instance;

    public static DefaultFieldFormatter instance() {
        if(instance == null) {
            instance = new DefaultFieldFormatter();
        }
        return instance;
    }

    private static final char attackArrow = '\u2197',
    swords = '\u2694',
    heart = '\u2665';
    private static final String shoe = "\uD83D\uDC5E";

    private DefaultFieldFormatter() {
    }

    @Override
    public FieldSection formatField(Field field) {
        List<LineSection> header = List.of(
                new LineSection("", field.terrain().name(), "")
        );
        List<LineSection> center = new ArrayList<>(), bottom = new ArrayList<>();
        Machine m = field.machine();
        if(m != null) {
            center = List.of(
                    new LineSection("", m.name(), ""),
                    new LineSection("", "" + m.orientation().descriptor(), "")
            );
            bottom = List.of(
                    new LineSection("" + swords + m.strength(), shoe + m.moveRange(), "" + heart + m.health())
            );
        }
        return new FieldSection(header, center, bottom);
    }
}
