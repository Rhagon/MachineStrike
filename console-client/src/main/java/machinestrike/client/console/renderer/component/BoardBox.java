package machinestrike.client.console.renderer.component;

import machinestrike.game.Point;
import machinestrike.game.level.Board;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BoardBox extends Decorator {

    @Nullable
    private Board board;
    @NotNull
    private final BoxPanel outlinePanel;
    @NotNull
    private final List<FieldBox> fields;
    private float fieldSizeRatio;
    private int fieldWidth, fieldHeight;

    public BoardBox(@Nullable Board board, float fieldSizeRatio) {
        super();
        this.board = board;
        this.outlinePanel = new BoxPanel(new BoxPanel.Outline('-', '|', '+', 1, 1));
        this.fieldSizeRatio = fieldSizeRatio;
        this.fieldWidth = 0;
        this.fieldHeight = 0;
        fields = new ArrayList<>();
        child(outlinePanel);
        rebuild();
    }

    public int fieldWidth() {
        return fieldWidth;
    }

    public int fieldHeight() {
        return fieldHeight;
    }

    public void board(@Nullable Board board) {
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
        if(board != null) {
            for (int i = 0; i < board.sizeX(); ++i) {
                for (int j = 0; j < board.sizeY(); ++j) {
                    FieldBox box = new FieldBox(board.field(i, j));
                    box.anchor(Anchor.BOTTOM_LEFT);
                    fields.add(box);
                    outlinePanel.add(box);
                }
            }
        }
        onLayoutChange();
        update();
    }

    @Override
    public void onLayoutChange() {
        updateSize();
        if(board == null) {
            outlinePanel.anchor(Anchor.TOP_LEFT.size(0, 0));
        } else {
            outlinePanel.anchor(Anchor.TOP_LEFT.size(fieldWidth * board.sizeX() + 2, fieldHeight * board.sizeY() + 2));
            if (board != null) {
                for (FieldBox box : fields) {
                    Point fPos = box.field().position();
                    box.anchor(box.anchor().size(fieldWidth, fieldHeight).position(fPos.x() * fieldWidth + 1, -fPos.y() * fieldHeight - 1));
                }
            }
        }
        super.onLayoutChange();
    }

    private void updateSize() {
        if(board == null) {
            fieldWidth = 0;
            fieldHeight = 0;
            return;
        }
        int maxFieldWidth = (size().x() - 2) / board.sizeX();
        int maxFieldHeight = (size().y() - 2) / board.sizeY();
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
