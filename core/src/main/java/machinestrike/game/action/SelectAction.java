package machinestrike.game.action;

import machinestrike.game.Player;
import machinestrike.game.Point;
import machinestrike.game.rule.RuleViolation;
import machinestrike.game.statemachine.State;

public record SelectAction(Player player, Point position) implements Action {

    @Override
    public void execute(State state) throws RuleViolation {
        state.handle(this);
    }

}
