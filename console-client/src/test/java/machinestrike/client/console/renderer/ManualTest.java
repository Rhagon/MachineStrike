package machinestrike.client.console.renderer;

import machinestrike.client.console.renderer.component.*;

public class ManualTest {

    public static void main(String[] args) {
        final int w = 100, h = 20;
        Canvas canvas = new Canvas(w, h);

        canvas.fillRect(0, 0, w, h, '#');
        canvas.fillRect(2, 1, w - 4, h - 2, ' ');

        Label label = new Label("Hello,\nWorld");
        label.anchor(Anchor.AREA.pad(1, 2, 2, 1));
        label.alignment(Label.Alignment.CENTER);

        Panel box = new BoxPanel('-', new BoxPanel.Outline('=', '|', '#', 2, 1));
        box.anchor(Anchor.CENTER);
        box.add(label);

        canvas.stage(box);

        canvas.repaint();

        System.out.println(canvas);

        label.text("New\nText");

        System.out.println(canvas);

        canvas.repaint();

        System.out.println(canvas);
    }

}
