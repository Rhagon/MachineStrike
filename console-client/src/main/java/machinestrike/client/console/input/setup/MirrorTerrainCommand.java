package machinestrike.client.console.input.setup;

import machinestrike.client.console.action.setup.MirrorTerrainAction;
import machinestrike.client.console.action.setup.SetupActionHandler;
import machinestrike.client.console.input.Command;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;

public class MirrorTerrainCommand extends Command<SetupActionHandler> {

    private static MirrorTerrainCommand instance;

    public static MirrorTerrainCommand instance() {
        if(instance == null) {
            instance = new MirrorTerrainCommand();
        }
        return instance;
    }

    private MirrorTerrainCommand() {
        super("mirror terrain", "mirror terrain");
    }

    @Override
    protected @Nullable MirrorTerrainAction parse(@NotNull Matcher matcher) {
        return new MirrorTerrainAction();
    }
}
