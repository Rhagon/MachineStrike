package machinestrike.game.statemachine;

import machinestrike.debug.Assert;
import machinestrike.game.Player;
import machinestrike.game.action.*;
import machinestrike.game.machine.Machine;
import machinestrike.game.rule.RuleViolation;
import machinestrike.game.rule.moverule.MoveRule;
import org.jetbrains.annotations.NotNull;

public class PlayerTurnState extends State{

    @NotNull
    private final Player playerOnTurn;

    public PlayerTurnState(@NotNull StateMachine stateMachine, @NotNull Player playerOnTurn) {
        super(stateMachine);
        this.playerOnTurn = playerOnTurn;
    }

    public Player playerOnTurn() {
        return playerOnTurn;
    }

    @Override
    public void handle(@NotNull AttackAction action) {

    }

    @Override
    public void handle(@NotNull DropAction action) {

    }

    @Override
    public void handle(@NotNull MoveAction action) throws RuleViolation {
        game().ruleBook().verifyMove(game(), action);
        Machine machine = board().field(action.origin()).machine();
        Assert.notNullRequired(machine);
        if(machine.hasMoved()) {
            machine.overcharge();
        }
        machine.move();
        board().field(action.origin()).machine(null);
        board().field(action.destination()).machine(machine);
        machine.orientation(action.orientation());
    }

    @Override
    public void handle(@NotNull OverchargeAction action) {

    }

    @Override
    public void handle(@NotNull SelectAction action) {

    }
}
