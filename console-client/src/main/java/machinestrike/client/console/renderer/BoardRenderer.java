package machinestrike.client.console.renderer;

import machinestrike.game.level.Board;

import java.util.ArrayList;
import java.util.List;

public class BoardRenderer {

    private final int fieldWidth, fieldHeight;
    private final FieldFormatter formatter;

    public BoardRenderer(int fieldWidth, int fieldHeight, FieldFormatter formatter) {
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
        this.formatter = formatter;
    }

    public String printBoard(Board board) {
        StringBuilder builder = new StringBuilder();
        addHorizontalLine(builder, board.sizeX());
        for(int y = board.sizeY() - 1; y >= 0; --y) {
            List<String[]> fields = new ArrayList<>();
            for(int x = 0; x < board.sizeX(); ++x) {
                fields.add(printField(formatter.formatField(board.field(x, y))));
            }
            addRows(builder, fields);
            addHorizontalLine(builder, board.sizeX());
        }
        return builder.toString();
    }

    private void addHorizontalLine(StringBuilder builder, int boardWidth) {
        builder.append("+");
        builder.append(("-".repeat(fieldWidth) + "+").repeat(boardWidth));
        builder.append("\n");
    }

    private void addRows(StringBuilder builder, List<String[]> fields) {
        for(int lineCounter = 0; lineCounter < fieldHeight; ++lineCounter) {
            builder.append("|");
            for (String[] array : fields) {
                builder.append(array[lineCounter]);
                builder.append("|");
            }
            builder.append("\n");
        }
    }

    private String[] printField(FieldSection section) {
        int centerLines = Math.min(section.center().size(), fieldHeight);
        int borderLines = fieldHeight - centerLines;
        int topLines = Math.min(section.top().size(), borderLines/2);
        int topFillerLines = borderLines/2 - topLines;
        int bottomLines = Math.min(section.bottom().size(), borderLines - borderLines/2);
        int bottomFillerLines = borderLines - borderLines/2 - bottomLines;

        List<String> lines = new ArrayList<>();
        for(int i = 0; i < topLines; ++i) {
            lines.add(printLine(section.top().get(i)));
        }
        String fillerLine = " ".repeat(fieldWidth);
        for(int i = 0; i < topFillerLines; ++i) {
            lines.add(fillerLine);
        }
        for(int i = 0; i < centerLines; ++i) {
            lines.add(printLine(section.center().get(i)));
        }
        for(int i = 0; i < bottomFillerLines; ++i) {
            lines.add(fillerLine);
        }
        for(int i = 0; i < bottomLines; ++i) {
            lines.add(printLine(section.bottom().get(i)));
        }
        return lines.toArray(new String[0]);
    }

    private String printLine(LineSection section) {
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
