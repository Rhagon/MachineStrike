package machinestrike.client.console.input.parser;

import machinestrike.game.action.Action;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;

public interface ActionParser<T extends Action> {

    @NotNull
    T parse(@NotNull Matcher matcher);

}
