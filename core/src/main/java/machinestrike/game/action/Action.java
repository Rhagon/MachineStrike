package machinestrike.game.action;

import machinestrike.game.Game;
import machinestrike.game.rule.RuleViolation;

public interface Action {

    void execute(Game game) throws RuleViolation;

}
