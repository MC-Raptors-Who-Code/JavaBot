package RWC.BotCommand;

import java.util.ArrayList;
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
	
	//The following ArrayLists hold commands based on respective category
	private static ArrayList<Command> adminCommands = new ArrayList<Command>();
	private static ArrayList<Command> generalCommands = new ArrayList<Command>();
	
	/**
	 * Call addCommand for each command
	 */
	public CommandManager() {
		addCommand(new Help());
		addCommand(new Meet());
		addCommand(new Clear());
	}
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String message = event.getMessage().getContentRaw();
		if(message.startsWith(Config.PREFIX)) {
			//Create an array with all arguments of the message
			message = message.replace(Config.PREFIX, "");
			String[] args = message.split("\\s+");
			String commandName = args[0].toLowerCase();
			
			//Check if a command by that name exists
			if (commands.containsKey(commandName)) {
				commands.get(commandName).onGuildMessageReceived(event, args);
			} else {
				event.getChannel().sendMessage(commandName + " is not a command.").queue();
			}
		}
	}
	
	/**
	 * Adds a command to the command map and to its category ArrayList
	 * @param command
	 */
	private void addCommand(Command command) {
		if (!commands.containsKey(command.getName())) {
			//Add to command map
			commands.put(command.getName().toLowerCase(), command);
			
			//Determine which ArrayList the command should be added to
			int category = command.getCategory();
			switch (category) {
			case 0:
				adminCommands.add(command);
				break;
			case 1:
				generalCommands.add(command);
				break;
			default:
				System.out.println(command.getName() 
						+ " has not been assigned to a valid category and will not appear in the help menu until fixed.");
			}
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

	public static ArrayList<Command> getAdminCmd() {
		return adminCommands;
	}

	public static ArrayList<Command> getGenCmd() {
		return generalCommands;
	}

}
