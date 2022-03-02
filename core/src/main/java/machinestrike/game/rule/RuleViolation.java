package machinestrike.game.rule;

public class RuleViolation extends Throwable {

    public RuleViolation(String message) {
        super(message);
    }

    public RuleViolation() {
        this("Action violated a rule and was ");
    }

}
