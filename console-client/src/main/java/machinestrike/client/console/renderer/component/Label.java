package machinestrike.client.console.renderer.component;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class Label extends Component{

    @NotNull
    private String text;
    private char[][] content;

    public Label() {
        this.text = "";
        this.content = new char[0][0];
    }

    public Label(@NotNull String text) {
        this();
        setText(text);
    }

    public void setText(@NotNull String text) {
        int opw = prefWidth();
        int oph = prefHeight();
        this.text = text;
        String[] c = text.split("\\n");
        int maxWidth = 0;
        for (String line : c) {
            if (line.length() > maxWidth) {
                maxWidth = line.length();
            }
        }
        prefWidth(maxWidth);
        prefHeight(c.length);
        content = new char[c.length][maxWidth];
        for (int i = 0; i < c.length; ++i) {
            System.arraycopy(c[i].toCharArray(), 0, content[i], 0, c[i].length());
            Arrays.fill(content[i], c[i].length(), maxWidth, ' ');
        }
        if(opw != prefWidth() || oph != prefHeight()) {
            updatePrefSize();
        }
    }

    @Override
    public void render(char[][] canvas, int x, int y, int width, int height) {
        for(int i = 0; i < Math.min(prefHeight(), height); ++i) {
            char[] line = content[i];
            System.arraycopy(line, 0, canvas[y + i], x, Math.min(prefWidth(), width));
        }
    }

    @Override
    public void updatePrefSize() {
        if(parent() != null) {
            parent().updatePrefSize();
        }
    }

}
