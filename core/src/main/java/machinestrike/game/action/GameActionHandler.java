package machinestrike.game.action;

import machinestrike.action.ActionExecutionFailure;
import org.jetbrains.annotations.NotNull;

public interface GameActionHandler {

    void handle(@NotNull AttackAction action) throws ActionExecutionFailure;

    void handle(@NotNull MoveAction action) throws ActionExecutionFailure;

    void handle(@NotNull EndTurnAction action) throws ActionExecutionFailure;

}
