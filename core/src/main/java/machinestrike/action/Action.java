package machinestrike.action;

import org.jetbrains.annotations.NotNull;

public interface Action<HandlerType> {

    void execute(@NotNull HandlerType handler) throws ActionExecutionFailure;

}
