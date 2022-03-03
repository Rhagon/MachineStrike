package machinestrike.client.console.input.parser;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;

public interface Parser<T> {

    @NotNull
    T parse(@NotNull Matcher matcher);

}
