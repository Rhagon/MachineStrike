package machinestrike.client.console.input.factory;

import machinestrike.client.console.action.HelpAction;
import machinestrike.client.console.action.QuitAction;
import machinestrike.client.console.action.RedrawAction;
import machinestrike.client.console.input.Command;
import machinestrike.client.console.input.parser.AttackActionParser;
import machinestrike.client.console.input.parser.MoveActionParser;
import machinestrike.client.console.input.parser.SprintActionParser;

import java.util.List;
import java.util.regex.Pattern;

public class DefaultCommandListFactory implements CommandListFactory {

    private static DefaultCommandListFactory instance;

    private static final String abstractMovePattern = "(?<oc>[A-Za-z])(?<or>[1-9][0-9]*)\\sto\\s(?<dc>[A-Za-z])(?<dr>[1-9][0-9]*)\\sfacing\\s(?<dir>[nwse])";
    private static final Pattern movePattern = Pattern.compile("move\\s" + abstractMovePattern);
    private static final Pattern sprintPattern = Pattern.compile("sprint\\s" + abstractMovePattern);
    private static final Pattern attackPattern = Pattern.compile("attack\\swith\\s(?<oc>[A-Za-z])(?<or>[1-9][0-9]*)");

    public static DefaultCommandListFactory instance() {
        if(instance == null) {
            instance = new DefaultCommandListFactory();
        }
        return instance;
    }

    private DefaultCommandListFactory() {
    }

    @Override
    public List<Command<?>> createCommandList() {
        return List.of(
                new Command<>("quit|q|exit|stop", m -> new QuitAction(), "quit"),
                new Command<>("redraw", m -> new RedrawAction(), "redraw"),
                new Command<>("help|h|\\?", m -> new HelpAction(), "help"),
                new Command<>(movePattern, MoveActionParser.instance(), "move <field> to <field> [facing <orientation>]"),
                new Command<>(sprintPattern, SprintActionParser.instance(), "sprint <field> to <field> [facing <orientation>]"),
                new Command<>(attackPattern, AttackActionParser.instance(), "attack with <field>")
        );
    }
}
