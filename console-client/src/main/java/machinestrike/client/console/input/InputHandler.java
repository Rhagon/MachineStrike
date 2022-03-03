package machinestrike.client.console.input;

import machinestrike.util.ActionUnion;
import machinestrike.client.console.action.ConsoleActionHandler;
import machinestrike.game.action.GameActionHandler;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class InputHandler implements Iterable<ActionUnion<GameActionHandler, ConsoleActionHandler>> {

    @NotNull
    private final BufferedReader reader;
    @NotNull
    private final PrintStream output;
    @NotNull
    private final List<Command> commands;
    private boolean active;

    public InputHandler(@NotNull InputStream input, @NotNull PrintStream output, @NotNull List<Command> commands) {
        reader = new BufferedReader(new InputStreamReader(input));
        this.output = output;
        this.commands = commands;
        this.active = true;
    }

    @Contract(pure = true)
    public List<Command> commands() {
        return Collections.unmodifiableList(commands);
    }

    @Contract(pure = true)
    public boolean active() {
        return active;
    }

    @Contract("_ -> this")
    public InputHandler active(boolean active) {
        this.active = active;
        return this;
    }

    @NotNull
    @Override
    public Iterator<ActionUnion<GameActionHandler, ConsoleActionHandler>> iterator() {
        return new Iterator<>() {
            ActionUnion<GameActionHandler, ConsoleActionHandler> buffer = null;
            @Override
            public boolean hasNext() {
                if(!active) {
                    return false;
                }
                if(buffer == null) {
                    buffer = readAction();
                }
                return buffer != null;
            }
            @Override
            public ActionUnion<GameActionHandler, ConsoleActionHandler> next() {
                if(buffer == null) {
                    buffer = readAction();
                }
                ActionUnion<GameActionHandler, ConsoleActionHandler> a = buffer;
                buffer = null;
                return a;
            }
        };
    }

    @Nullable
    private ActionUnion<GameActionHandler, ConsoleActionHandler> readAction() {
        while(true) {
            String line;
            try {
                line = reader.readLine();
            } catch (IOException e) {
                return null;
            }
            if(line == null) {
                return null;
            }
            for(Command command : commands) {
                ActionUnion<GameActionHandler, ConsoleActionHandler> action = command.parse(line);
                if(action != null) {
                    return action;
                }
            }
            output.println("Unknown command");
        }
    }

}
