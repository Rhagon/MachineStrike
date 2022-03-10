package machinestrike.client.console.statemachine.client;

import machinestrike.action.Action;
import machinestrike.client.console.action.setup.SetupActionHandler;
import machinestrike.client.console.command.factory.SetupCommandFactory;
import machinestrike.client.console.input.InputHandler;
import machinestrike.game.rule.RuleViolation;
import org.jetbrains.annotations.NotNull;

public class SetupState extends ClientState implements SetupActionHandler {

    public SetupState(@NotNull ClientStateMachine stateMachine) {
        super(stateMachine);
    }

    @Override
    public void exec() {
        InputHandler<SetupActionHandler> input = new InputHandler<>(stateMachine().client().input(), SetupCommandFactory.instance().create());
        for(Action<? super SetupActionHandler> action : input) {
            try {
                action.execute(this);
            } catch (RuleViolation e) {
                stateMachine().client().info(e.getMessage());
            }
            if(input.active()) {
                stateMachine().client().render();
            }
        }
    }
}
