package machinestrike.client.console.renderer.component;

import machinestrike.client.console.renderer.Anchor;
import machinestrike.client.console.renderer.color.Colors;
import machinestrike.game.Game;
import machinestrike.game.Player;
import org.jetbrains.annotations.NotNull;

public class GameBox extends BoxPanel {

    private static final String emptyPoint = " \u25C7 ", fullPoint = " \u25C8 ";

    @NotNull
    private Game game;
    @NotNull
    private final Label redPointsLabel, bluePointsLabel;

    public GameBox(@NotNull Game game) {
        super(new Outline('-', '|', '+', 1, 1));
        this.game = game;
        bluePointsLabel = new Label("", Label.Alignment.TOP_LEFT, Colors.BLUE);
        bluePointsLabel.anchor(Anchor.TOP_EDGE.size(0, 1).pad(0, 1, 1, 0).position(0, 1));
        redPointsLabel = new Label("", Label.Alignment.TOP_RIGHT, Colors.RED);
        redPointsLabel.anchor(Anchor.TOP_EDGE.size(0, 1).pad(0, 1, 1, 0).position(0, 1));
        add(bluePointsLabel);
        add(redPointsLabel);
    }

    public void game(@NotNull Game game) {
        this.game = game;
        update();
    }

    public void update() {
        int blueFull = Math.min(game.victoryPoints(Player.BLUE), game.ruleBook().requiredVictoryPoints());
        int blueEmpty = game.ruleBook().requiredVictoryPoints() - blueFull;
        bluePointsLabel.text(fullPoint.repeat(blueFull) + emptyPoint.repeat(blueEmpty));
        int redFull = Math.min(game.victoryPoints(Player.RED), game.ruleBook().requiredVictoryPoints());
        int redEmpty = game.ruleBook().requiredVictoryPoints() - redFull;
        redPointsLabel.text(emptyPoint.repeat(redEmpty) + fullPoint.repeat(redFull));
    }

}
