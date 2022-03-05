package machinestrike.client.console.renderer.component;

import machinestrike.game.level.Board;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BoardBox extends Component implements Container {

    @Nullable
    private Board board;
    private int fieldWidth, fieldHeight;
    private FieldBox[][] fields;

    public BoardBox(@Nullable Board board, int fieldWidth, int fieldHeight) {
        this.board = board;
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
    }

    public void update() {
        for(FieldBox[] strip : fields) {
            for(FieldBox field : strip) {
                field.update();
            }
        }
    }

    private void build() {
        if(board == null) {
            fields = new FieldBox[0][0];
        } else {
            fields = new FieldBox[board.sizeX()][board.sizeY()];
            for(int i = 0; i < board.sizeX(); ++i) {
                for(int j = 0; j < board.sizeY(); ++j) {
                    fields[i][j] = new FieldBox(this, board.field(i, j));
                }
            }
        }
        updatePrefSize();
    }

    @Contract(pure = true)
    public int fieldWidth() {
        return fieldWidth;
    }

    @Contract("_ -> this")
    public BoardBox fieldWidth(int fieldWidth) {
        this.fieldWidth = fieldWidth;
        updatePrefSize();
        return this;
    }

    @Contract("_ -> this")
    public BoardBox fieldHeight(int fieldHeight) {
        this.fieldHeight = fieldHeight;
        updatePrefSize();
        return this;
    }

    @Contract("_ -> this")
    public BoardBox board(@Nullable Board board) {
        this.board = board;
        build();
        return this;
    }

    @Contract(pure = true)
    public int fieldHeight() {
        return fieldHeight;
    }

    @Override
    @Contract(pure = true)
    public List<Component> children() {
        List<Component> children = new ArrayList<>();
        for(FieldBox[] strip : fields) {
            children.addAll(List.of(strip));
        }
        return children;
    }

    @Override
    @Contract(pure = true)
    public int childCount() {
        if(board == null) {
            return 0;
        }
        return board.sizeY() + board.sizeX();
    }

    @Override
    public void render(char[][] canvas, int x, int y, int width, int height) {

    }

    @Override
    public void updatePrefSize() {
        if(board == null) {
            prefWidth(0);
            prefHeight(0);
            return;
        }
        prefWidth((1 + fieldWidth) * board.sizeX() + 1);
        prefHeight((1 + fieldHeight) * board.sizeY() + 1);
        if(parent() != null) {
            parent().updatePrefSize();
        }
    }

    @Override
    public Container remove(@NotNull Component child) {
        throw new UnsupportedOperationException();
    }
}
