package machinestrike.client.console.renderer;

import org.jetbrains.annotations.NotNull;

import java.io.PrintStream;

public class RenderPrintStream implements RenderStream {

    private final PrintStream stream;

    public RenderPrintStream(@NotNull PrintStream stream) {
        this.stream = stream;
    }

    @Override
    public void clear() {
    }

    @Override
    public void print(Object obj) {
        stream.print(obj);
    }
}
