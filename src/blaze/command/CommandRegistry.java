package src.blaze.command;

import java.util.HashMap;

public class CommandRegistry {

    private static final HashMap<String, CommandTemplate> commands = new HashMap<>();

    public static void registerCommandTemplate(CommandTemplate command) {
        commands.putIfAbsent(command.getCommandName(), command);
    }

    public static CommandTemplate getCommand(String commandName) {
        return commands.get(commandName);
    }
}
