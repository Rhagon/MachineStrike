package machinestrike.client.console.input;

import machinestrike.action.Action;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class InputHandler<HandlerType> implements Iterable<Action<? super HandlerType>> {

    @NotNull
    private final BufferedReader reader;
    @Nullable
    private final PrintStream interaction;
    @NotNull
    private final List<Command<? super HandlerType>> commands;
    private boolean active;

    public InputHandler(@NotNull InputStream input, @NotNull List<Command<? super HandlerType>> commands) {
        this(input, null, commands);
    }

    public InputHandler(@NotNull InputStream input, @Nullable PrintStream interaction, @NotNull List<Command<? super HandlerType>> commands) {
        this.reader = new BufferedReader(new InputStreamReader(input));
        this.interaction = interaction;
        this.commands = commands;
        this.active = true;
    }

    @Contract(pure = true)
    public List<Command<? super HandlerType>> commands() {
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
                if(interaction != null) {
                    interaction.print("> ");
                }
                line = reader.readLine();
            } catch (IOException e) {
                return null;
            }
            if(line == null) {
                return null;
            }
            for(Command<? super HandlerType> command : commands) {
                Action<? super HandlerType> action = command.parse(line);
                if(action != null) {
                    return action;
                }
            }
        }
    }

}
