package machinestrike.client.console.renderer;

import machinestrike.client.console.infoboard.InfoBoard;
import machinestrike.game.level.Board;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Renderer {

    private final int fieldWidth, fieldHeight, infoWidth;
    @NotNull
    private final FieldFormatter formatter;
    @Nullable
    private Board board;
    @Nullable
    private InfoBoard info;
    @NotNull
    private final RenderStream output;

    public Renderer(@Nullable Board board, @Nullable InfoBoard info, @NotNull RenderStream output, int fieldWidth,
                    int fieldHeight, int infoWidth, @NotNull FieldFormatter formatter) {
        this.board = board;
        this.info = info;
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
        this.infoWidth = infoWidth;
        this.formatter = formatter;
        this.output = output;
    }

    @Contract("_ -> this")
    public Renderer board(@Nullable Board board) {
        this.board = board;
        return this;
    }

    @Contract("_ -> this")
    public Renderer info(@Nullable InfoBoard info) {
        this.info = info;
        return this;
    }

    public void render() {
        if(board == null) {
            return;
        }
        StringBuilder builder = new StringBuilder();
        renderRowSeparator(builder, board.sizeX());
        for(int y = board.sizeY() - 1; y >= 0; --y) {
            List<String[]> fields = new ArrayList<>();
            for(int x = 0; x < board.sizeX(); ++x) {
                fields.add(renderField(formatter.formatField(board.field(x, y))));
            }
            renderFieldRow(builder, fields, y);
            renderRowSeparator(builder, board.sizeX());
        }
        renderColumnDescription(builder, board.sizeX());
        output.clear();
        output.print(builder.toString());
    }

    private void renderRowSeparator(StringBuilder builder, int boardWidth) {
        builder.append("  +");
        builder.append(("-".repeat(fieldWidth) + "+").repeat(boardWidth));
        builder.append("\n");
    }

    private void renderColumnDescription(StringBuilder builder, int columnCount) {
        builder.append("   ");
        for(int i = 0; i < columnCount; ++i) {
            char descriptor = (char) ('A' + i);
            LineSection section = new LineSection("", "" + descriptor, "");
            builder.append(renderLine(section));
            builder.append(" ");
        }
        builder.append("\n");
    }

    private void renderFieldRow(StringBuilder builder, List<String[]> fields, int rowNumber) {
        for(int lineCounter = 0; lineCounter < fieldHeight; ++lineCounter) {
            builder.append(lineCounter == fieldHeight/2 ? rowNumber + 1 + " |" : "  |");
            for (String[] array : fields) {
                builder.append(array[lineCounter]);
                builder.append("|");
            }
            builder.append("\n");
        }
    }

    private String[] renderField(FieldSection section) {
        int centerLines = Math.min(section.center().size(), fieldHeight);
        int borderLines = fieldHeight - centerLines;
        int topLines = Math.min(section.top().size(), borderLines/2);
        int topFillerLines = borderLines/2 - topLines;
        int bottomLines = Math.min(section.bottom().size(), borderLines - borderLines/2);
        int bottomFillerLines = borderLines - borderLines/2 - bottomLines;

        List<String> lines = new ArrayList<>();
        for(int i = 0; i < topLines; ++i) {
            lines.add(renderLine(section.top().get(i)));
        }
        String fillerLine = " ".repeat(fieldWidth);
        for(int i = 0; i < topFillerLines; ++i) {
            lines.add(fillerLine);
        }
        for(int i = 0; i < centerLines; ++i) {
            lines.add(renderLine(section.center().get(i)));
        }
        for(int i = 0; i < bottomFillerLines; ++i) {
            lines.add(fillerLine);
        }
        for(int i = 0; i < bottomLines; ++i) {
            lines.add(renderLine(section.bottom().get(i)));
        }
        return lines.toArray(new String[0]);
    }

    private String renderLine(LineSection section) {
        int center = Math.min(section.center().length(), fieldWidth);
        int border = fieldWidth - center;
        int left = Math.min(section.left().length(), border/2);
        int leftFiller = border/2 - left;
        int right = Math.min(section.right().length(), border - border/2);
        int rightFiller = border - border/2 - right;
        String leftSegment = section.left().substring(0, left);
        String centerSegment = section.center().substring(0, center);
        String rightSegment = section.right().substring(0, right);
        return leftSegment + " ".repeat(leftFiller) + centerSegment + " ".repeat(rightFiller) + rightSegment;
    }

}
