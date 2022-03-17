package machinestrike.action;

public class ActionExecutionFailure extends Throwable {

    public ActionExecutionFailure(String message) {
        super(message);
    }

    public ActionExecutionFailure() {
        this("The action could not be executed.");
    }

}
