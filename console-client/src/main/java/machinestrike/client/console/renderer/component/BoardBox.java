package machinestrike.client.console.renderer.component;

import machinestrike.game.Point;
import machinestrike.game.level.Board;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BoardBox extends Panel {

    @Nullable
    private Board board;

    @NotNull
    private final List<FieldBox> fields;

    public BoardBox(@Nullable Board board) {
        this.board = board;
        fields = new ArrayList<>();
        rebuild();
    }

    public void board(@Nullable Board board) {
        this.board = board;
        rebuild();
    }

    public void rebuild() {
        for(Component child : children()) {
            remove(child);
        }
        if(board == null) {
            return;
        }
        for(int i = 0; i < board.sizeX(); ++i) {
            for(int j = 0; j < board.sizeY(); ++j) {
                FieldBox box = new FieldBox(board.field(i, j));
                box.anchor(Anchor.BOTTOM_LEFT);
                fields.add(box);
                add(box);
            }
        }
        onLayoutChange();
    }

    @Override
    public void onLayoutChange() {
        if(board != null) {
            int fieldWidth = size().x() / board.sizeX();
            int fieldHeight = size().y() / board.sizeY();
            for(FieldBox box : fields) {
                Point fPos = box.field().position();
                box.anchor(box.anchor().size(fieldWidth, fieldHeight).position(fPos.x() * fieldWidth, -fPos.y() * fieldHeight));
            }
        }
        super.onLayoutChange();
    }

    public void update() {
        for(FieldBox box : fields) {
            box.update();
        }
    }

}
