package machinestrike.client.console;

import machinestrike.game.level.Field;
import machinestrike.game.machine.Machine;

import java.util.ArrayList;
import java.util.List;

public class DefaultFieldFormatter implements FieldFormatter {

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
                    new LineSection("\u2197" + m.strength(), "", "\u2665" + m.health())
            );
        }
        return new FieldSection(header, center, bottom);
    }
}
