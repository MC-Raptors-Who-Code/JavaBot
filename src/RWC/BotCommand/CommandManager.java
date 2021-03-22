package RWC.BotCommand;

import java.util.HashMap;
import java.util.Map;

import RWC.Bot.Config;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * Manages all command objects
 * @author Rose Griffin
 *
 */
public class CommandManager extends ListenerAdapter {
	
	/**
	 * Map that holds all bot commands. The key is the command name as a string, while the value
	 * is a command object
	 */
	private static Map<String, Command> commands = new HashMap<String, Command>();
	
	/**
	 * Add all commands to command map
	 */
	public CommandManager() {
		addCommand(new Clear());
		addCommand(new Meet());
		addCommand(new Help());
		addCommand(new ChangePrefix());
	}
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String message = event.getMessage().getContentRaw();
		if(message.startsWith(Config.prefix)) {
			//Create an array with all arguments of the message
			message = message.replace(Config.prefix, "");
			String[] args = message.split("\\s+");
			String commandName = args[0].toLowerCase();
			
			//Check if a command by that name exists
			if (commands.containsKey(commandName)) {
				commands.get(commandName).onGuildMessageReceived(event, args);
			} else {
				System.out.println(commandName + " is not a command.");
			}
		}
	}
	
	/**
	 * Adds a command to the command map
	 * @param command
	 */
	private void addCommand(Command command) {
		if (!commands.containsKey(command.getName())) {
			commands.put(command.getName().toLowerCase(), command);
		} else {
			System.out.println("Unable to add " + command.getName() + " because a command with the same name already exists.");
		}
	}
	
	/**
	 * Gets the corresponding command object when given its name
	 * @param name
	 * @return command
	 */
	public Command getCommand(String name) {
		return commands.get(name);
	}
	
	/**
	 * Gets the map of all commands
	 */
	public static Map<String, Command> getCommands(){
		return commands;
	}

}
