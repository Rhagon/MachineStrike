package machinestrike.game.rule.moverule;

import machinestrike.game.Game;
import machinestrike.game.action.AttackAction;
import machinestrike.game.action.MoveAction;
import machinestrike.game.rule.attackrule.AttackRule;
import org.jetbrains.annotations.NotNull;

public class GameNotOverRule {

    public static MoveRule moveInstance() {
        if(GameNotOverMoveRule.instance == null) {
            GameNotOverMoveRule.instance = new GameNotOverMoveRule();
        }
        return GameNotOverMoveRule.instance;
    }

    public static AttackRule attackInstance() {
        if(GameNotOverAttackRule.instance == null) {
            GameNotOverAttackRule.instance = new GameNotOverAttackRule();
        }
        return GameNotOverAttackRule.instance;
    }

    private static class GameNotOverAttackRule implements AttackRule {

        private static GameNotOverAttackRule instance;

        @Override
        public @NotNull String errorMessage() {
            return "The game is already over.";
        }

        @Override
        public boolean test(Game game, AttackAction action) {
            return game.winner() == null;
        }
    }

    private static class GameNotOverMoveRule implements MoveRule {

        private static GameNotOverMoveRule instance;

        @Override
        public @NotNull String errorMessage() {
            return "The game is already over.";
        }

        @Override
        public boolean test(Game game, MoveAction action) {
            return game.winner() == null || action.virtualMove();
        }

    }

    private GameNotOverRule() {
    }

}
