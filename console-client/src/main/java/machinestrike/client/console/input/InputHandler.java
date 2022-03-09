package machinestrike.client.console.input;

import machinestrike.action.Action;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class InputHandler<HandlerType> implements Iterable<Action<? super HandlerType>> {

    @NotNull
    private final BufferedReader reader;
    @NotNull
    private final List<Command<HandlerType>> commands;
    private boolean active;

    public InputHandler(@NotNull InputStream input, @NotNull List<Command<HandlerType>> commands) {
        reader = new BufferedReader(new InputStreamReader(input));
        this.commands = commands;
        this.active = true;
    }

    @Contract(pure = true)
    public List<Command<HandlerType>> commands() {
        return Collections.unmodifiableList(commands);
    }

    @Contract(pure = true)
    public boolean active() {
        return active;
    }

    public void active(boolean active) {
        this.active = active;
    }

    @NotNull
    @Override
    public Iterator<Action<? super HandlerType>> iterator() {
        return new Iterator<>() {
            Action<? super HandlerType> buffer = null;
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
            public Action<? super HandlerType> next() {
                if(buffer == null) {
                    buffer = readAction();
                }
                Action<? super HandlerType> a = buffer;
                buffer = null;
                return a;
            }
        };
    }

    @Nullable
    private Action<? super HandlerType> readAction() {
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
            for(Command<HandlerType> command : commands) {
                Action<? super HandlerType> action = command.parse(line);
                if(action != null) {
                    return action;
                }
            }
        }
    }

}
