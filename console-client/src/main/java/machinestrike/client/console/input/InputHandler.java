package machinestrike.client.console.input;

import machinestrike.action.Action;
import machinestrike.client.console.action.ClientActionHandler;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class InputHandler implements Iterable<Action<? super ClientActionHandler>> {

    @NotNull
    private final BufferedReader reader;
    @NotNull
    private final List<Command<?>> commands;
    private boolean active;

    public InputHandler(@NotNull InputStream input, @NotNull List<Command<?>> commands) {
        reader = new BufferedReader(new InputStreamReader(input));
        this.commands = commands;
        this.active = true;
    }

    @Contract(pure = true)
    public List<Command<?>> commands() {
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
    public Iterator<Action<? super ClientActionHandler>> iterator() {
        return new Iterator<>() {
            Action<? super ClientActionHandler> buffer = null;
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
            public Action<? super ClientActionHandler> next() {
                if(buffer == null) {
                    buffer = readAction();
                }
                Action<? super ClientActionHandler> a = buffer;
                buffer = null;
                return a;
            }
        };
    }

    @Nullable
    private Action<? super ClientActionHandler> readAction() {
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
            for(Command<?> command : commands) {
                Action<? super ClientActionHandler> action = command.parse(line);
                if(action != null) {
                    return action;
                }
            }
        }
    }

}
