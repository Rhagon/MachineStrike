package machinestrike.client.console.input.parser;

import machinestrike.client.console.action.SetTerrainAction;
import machinestrike.game.Point;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;

public class SetTerrainActionParser implements Parser<SetTerrainAction>{

    private static SetTerrainActionParser instance;

    public static SetTerrainActionParser instance() {
        if(instance == null) {
            instance = new SetTerrainActionParser();
        }
        return instance;
    }

    private SetTerrainActionParser() {
    }

    @Override
    public @NotNull SetTerrainAction parse(@NotNull Matcher matcher) {
        int column = matcher.group("column").toUpperCase().charAt(0) - 'A';
        int row = Integer.parseInt(matcher.group("row")) - 1;
        String terrainName = matcher.group("type");
        return new SetTerrainAction(new Point(column, row), terrainName);
    }

}
