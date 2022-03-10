package machinestrike.client.console.renderer;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public record FieldSection(@NotNull List<LineSection> top, @NotNull List<LineSection> center, @NotNull List<LineSection> bottom) {
}
