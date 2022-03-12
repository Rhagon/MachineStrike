package machinestrike.client.console.renderer.component;

import machinestrike.client.console.renderer.Anchor;
import machinestrike.client.console.renderer.color.Colors;
import machinestrike.game.Point;
import machinestrike.game.level.Board;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BoardBox extends Decorator {

    private static final int H_PAD = 4, V_PAD = 1, OUTLINE_W = 1, OUTLINE_H = 1;
    @NotNull
    private Board board;
    @NotNull
    private final BoxPanel outlinePanel;
    @NotNull
    private final List<FieldBox> fields;
    @NotNull
    private final List<Label> rowLabels, columnLabels;

    private float fieldSizeRatio;
    private int fieldWidth, fieldHeight;

    public BoardBox(@NotNull Board board, float fieldSizeRatio) {
        super();
        this.board = board;
        this.outlinePanel = new BoxPanel(new BoxPanel.Outline('-', '|', '+', OUTLINE_W, OUTLINE_H));
        this.fieldSizeRatio = fieldSizeRatio;
        this.fieldWidth = 0;
        this.fieldHeight = 0;
        fields = new ArrayList<>();
        rowLabels = new ArrayList<>();
        columnLabels = new ArrayList<>();
        child(outlinePanel);
        rebuild();
    }

    public int fieldWidth() {
        return fieldWidth;
    }

    public int fieldHeight() {
        return fieldHeight;
    }

    public void board(@NotNull Board board) {
        this.board = board;
        rebuild();
    }

    public void fieldSizeRatio(float m) {
        this.fieldSizeRatio = m;
    }

    public void rebuild() {
        List<Component> outlinePanelChildren = outlinePanel.children();
        while(!outlinePanelChildren.isEmpty()) {
            outlinePanel.remove(0);
        }
        while(!rowLabels.isEmpty()) {
            rowLabels.remove(0);
        }
        while(!columnLabels.isEmpty()) {
            columnLabels.remove(0);
        }
        for (int i = 0; i < board.sizeX(); ++i) {
            for (int j = 0; j < board.sizeY(); ++j) {
                FieldBox box = new FieldBox(board.field(i, j));
                box.anchor(Anchor.BOTTOM_LEFT);
                fields.add(box);
                outlinePanel.add(box);
            }
        }
        for(int i = 0; i < board.sizeX(); ++i) {
            Label columnLabel = new Label("" + (char) ('A' + i), Label.Alignment.CENTER, Label.OverflowPolicy.TRUNC_LEFT, Colors.LABEL);
            columnLabels.add(columnLabel);
            outlinePanel.add(columnLabel);
        }
        for(int i = 0; i < board.sizeY(); ++i) {
            Label rowLabel = new Label("" + (char) ('1' + i), Label.Alignment.CENTER, Label.OverflowPolicy.TRUNC_LEFT, Colors.LABEL);
            rowLabels.add(rowLabel);
            outlinePanel.add(rowLabel);
        }
        onLayoutChange();
        update();
    }

    @Override
    public void onLayoutChange() {
        updateSize();
        outlinePanel.anchor(Anchor.BOTTOM_LEFT.size(
                fieldWidth * board.sizeX() + 2 * (H_PAD + OUTLINE_W),
                fieldHeight * board.sizeY() + 2 * (V_PAD + OUTLINE_H)));
        for (FieldBox box : fields) {
            Point fPos = box.field().position();
            box.anchor(box.anchor().size(fieldWidth, fieldHeight).position(
                    fPos.x() * fieldWidth + (H_PAD + OUTLINE_W),
                    -fPos.y() * fieldHeight - (V_PAD + OUTLINE_H)));
        }
        for(int i = 0; i < columnLabels.size(); ++i) {
            Label columnLabel = columnLabels.get(i);
            columnLabel.anchor(Anchor.BOTTOM_LEFT.size(fieldWidth, V_PAD).position(H_PAD + OUTLINE_W + i * fieldWidth, -OUTLINE_H));
        }
        for(int i = 0; i < rowLabels.size(); ++i) {
            Label rowLabel = rowLabels.get(i);
            rowLabel.anchor(Anchor.BOTTOM_LEFT.size(H_PAD, fieldHeight).position(OUTLINE_W, -V_PAD - OUTLINE_H - i * fieldHeight));
        }
        super.onLayoutChange();
    }

    private void updateSize() {
        int maxFieldWidth = (size().x() - 2 * (H_PAD + OUTLINE_W)) / board.sizeX();
        int maxFieldHeight = (size().y() - 2 * (V_PAD + OUTLINE_H)) / board.sizeY();
        if((int) (maxFieldHeight * fieldSizeRatio) <= maxFieldWidth) {
            fieldHeight = maxFieldHeight;
            fieldWidth = (int) (maxFieldHeight * fieldSizeRatio);
        } else {
            fieldWidth = maxFieldWidth;
            fieldHeight = (int) (maxFieldWidth / fieldSizeRatio);
        }
    }

    public void update() {
        for(FieldBox box : fields) {
            box.update();
        }
    }

}
