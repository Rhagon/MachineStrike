package machinestrike.game.action;

import machinestrike.game.rule.RuleViolation;
import machinestrike.game.statemachine.State;

public interface Action {

    void execute(State state) throws RuleViolation;

}
