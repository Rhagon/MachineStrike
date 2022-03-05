package machinestrike.client.console.renderer;

import machinestrike.client.console.renderer.component.BoxDecorator;
import machinestrike.client.console.renderer.component.HBox;
import machinestrike.client.console.renderer.component.Label;

public class ManualTest {

    public static void main(String[] args) {
        Label label = new Label( "Hello,\nWorld!");
        Label label2 = new Label("This\nis\na Test");
        HBox box = new HBox();
        BoxDecorator deco = new BoxDecorator(label2, 2, 1);
        box.separator(2).add(label).add(deco);
        CanvasRenderer renderer = new CanvasRenderer(20, 7, '*');
        System.out.println(renderer.render(box));
    }

}
