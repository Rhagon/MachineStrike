package machinestrike.client.console.renderer.component;

import machinestrike.game.level.Field;
import machinestrike.game.machine.Machine;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class FieldBox extends BorderBox {

    @NotNull
    private final BoardBox boardBox;
    @NotNull
    private final Field field;

    @NotNull
    private final Label terrainLabel, machineLabel, directionLabel, strengthLabel, moveLabel, healthLabel;
    @NotNull
    private final HBox statBox;
    @NotNull
    private final VBox headerBox;

    public FieldBox(@NotNull BoardBox boardBox, @NotNull Field field) {
        super(ExpansionPolicy.CENTER);
        this.boardBox = boardBox;
        this.field = field;
        terrainLabel = new Label();
        machineLabel = new Label();
        directionLabel = new Label();
        strengthLabel = new Label();
        moveLabel = new Label();
        healthLabel = new Label();
        statBox = new HBox();
        headerBox = new VBox();
        headerBox.add(terrainLabel).add(machineLabel);
        statBox.add(strengthLabel).add(moveLabel).add(healthLabel);
        child(Slot.TOP, headerBox);
        child(Slot.CENTER, directionLabel);
        child(Slot.BOTTOM, statBox);
    }

    public void update() {
        terrainLabel.setText(field.terrain().name());
        Machine machine = field.machine();
        if(machine != null) {
            machineLabel.setText(machine.name());
            directionLabel.setText(machine.orientation().descriptor() + "");
            strengthLabel.setText("" + machine.strength());
            moveLabel.setText("" + machine.moveRange());
            healthLabel.setText("" + machine.health());
        }
        machineLabel.setText("");
        directionLabel.setText("");
        strengthLabel.setText("");
        moveLabel.setText("");
        healthLabel.setText("");
    }

    @Override
    public void updatePrefSize() {
    }

    @Override
    @Contract(pure = true)
    public int prefWidth() {
        return boardBox.fieldWidth();
    }

    @Override
    @Contract(pure = true)
    public int prefHeight() {
        return boardBox.fieldHeight();
    }



}
