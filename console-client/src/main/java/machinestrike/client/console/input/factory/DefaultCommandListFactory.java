package machinestrike.client.console.input.factory;

import machinestrike.client.console.action.*;
import machinestrike.client.console.input.Command;
import machinestrike.client.console.input.parser.PlaceMachineActionParser;
import machinestrike.client.console.input.parser.SetTerrainActionParser;
import machinestrike.client.console.input.parser.SetWindowSizeActionParser;
import machinestrike.game.Point;

import java.util.List;
import java.util.regex.Pattern;

public class DefaultCommandListFactory implements CommandListFactory<ClientActionHandler> {

    private static DefaultCommandListFactory instance;

    private static final String fieldPattern = "(?<column>[A-Za-z])(?<row>[1-9][0-9]*)";
    private static final String orientationPattern = "(?<orientation>[nwse]|north|west|south|east)";
    private static final String abstractMovePattern = "(?<oc>[A-Za-z])(?<or>[1-9][0-9]*)\\sto\\s(?<dc>[A-Za-z])(?<dr>[1-9][0-9]*)\\sfacing\\s(?<dir>[nwse])";
    private static final Pattern movePattern = Pattern.compile("move\\s" + abstractMovePattern);
    private static final Pattern sprintPattern = Pattern.compile("sprint\\s" + abstractMovePattern);
    private static final Pattern attackPattern = Pattern.compile("attack\\swith\\s(?<oc>[A-Za-z])(?<or>[1-9][0-9]*)");
    private static final Pattern setWindowSizePattern = Pattern.compile("(?:set|change)\\swindow\\ssize\\sto\\s(?<w>[1-9][0-9]*)\\s(?<h>[1-9][0-9]*)");
    private static final Pattern setTerrainPattern = Pattern.compile("(?:set|change)\\sterrain\\sat\\s" + fieldPattern + "\\sto\\s(?<type>[A-Za-z]+)");
    private static final Pattern placeMachinePattern = Pattern.compile("place\\s(?<name>[A-Za-z]+)\\sat\\s" + fieldPattern + "(?:\\sfor\\s(?<player>blue|b|red|r))?(?:\\sfacing\\s" + orientationPattern + ")?");

    public static DefaultCommandListFactory instance() {
        if(instance == null) {
            instance = new DefaultCommandListFactory();
        }
        return instance;
    }

    private DefaultCommandListFactory() {
    }

    @Override
    public List<Command<ClientActionHandler>> createCommandList() {
        return List.of(
                new Command<>("quit|q|exit|stop", m -> new QuitAction(), "quit"),
                new Command<>("redraw", m -> new RedrawAction(), "redraw"),
                new Command<>("help|h|\\?", m -> new HelpAction(), "help"),
                //new Command<>(movePattern, MoveActionParser.instance(), "move <field> to <field> [facing <orientation>]"),
                //new Command<>(sprintPattern, SprintActionParser.instance(), "sprint <field> to <field> [facing <orientation>]"),
                //new Command<>(attackPattern, AttackActionParser.instance(), "attack with <field>"),
                new Command<>(setWindowSizePattern, SetWindowSizeActionParser.instance(), "set window size to <width> <height>"),
                //new Command<>(Pattern.compile("y|yes|n|no"), m -> new ConfirmationAction(m.group().contains("y")), "y|yes|n|no"),
                new Command<>(setTerrainPattern, SetTerrainActionParser.instance(), "change terrain at <position> to <type>"),
                new Command<>(Pattern.compile("new game"), m -> new NewGameAction(new Point(8, 8)), "new game"),
                new Command<>(Pattern.compile("mirror terrain"), m -> new MirrorTerrainAction(), "mirror terrain"),
                new Command<>(placeMachinePattern, PlaceMachineActionParser.instance(), "place <type|'none'> at <field> [for <player>] [facing <orientation>]"),
                new Command<>(Pattern.compile(".*"), m -> new UnknownCommandAction(m.group()), "")
        );
    }
}
