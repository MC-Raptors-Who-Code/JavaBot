package RWC.BotCommand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import RWC.Bot.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

/**
 * Displays name and description of each command in an embed
 * @author Rose Griffin
 *
 */
public class Help extends Command {
	
	private Map<String, Command> commands = new HashMap<String, Command>();
	private ArrayList<Command> adminCommands = new ArrayList<Command>();
	private ArrayList<Command> generalCommands = new ArrayList<Command>();
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent event, String[] args) {
		
		commands = CommandManager.getCommands();
		
		EmbedBuilder help = new EmbedBuilder();
		
		//Display help menu
		if(args.length == 1) {
			adminCommands = CommandManager.getAdminCmd();
			generalCommands = CommandManager.getGenCmd();
			
			help.setTitle("Help Menu");
			help.addField("Admin Commands", printMessage(adminCommands),false);
			help.addField("General Commands", printMessage(generalCommands),false);
			help.setColor(0x592e8e);
			help.setFooter("Here you go! For further information on a command type " + Config.prefix 
					+ "" + getName() + " [command]" ,event.getMember().getUser().getAvatarUrl());
			event.getChannel().sendMessage(help.build()).queue();
		
		//Display help for designated command
		} else if (args.length == 2) {
			try {
				Command command = commands.get(args[1]);
				
				
				help.setTitle("Help " + "" + Config.prefix + "" + command.getName());
				help.setDescription(command.getExample());
				help.setColor(0x592e8e);
				help.setFooter("Here you go!",event.getMember().getUser().getAvatarUrl());
				
				event.getChannel().sendMessage(help.build()).queue();
			} catch (Exception e) {
				
				
				help.setTitle("⚠Syntax Error");
				help.setDescription(args[1] + " is not a valid command");
				help.addField("Example", Config.prefix + "help clear",false);
				help.setColor(0xeb3434);
				
				event.getChannel().sendMessage(help.build()).queue();
				help.clear();
			}
		//Display when help command is used incorrectly
		} else {
			
			help.setTitle("⚠Syntax Error");
			help.setDescription("Too many arguments");
			help.addField("Example", Config.prefix + "help clear",false);
			help.setColor(0xeb3434);
			
			event.getChannel().sendMessage(help.build()).queue();
			help.clear();
		}
	}
	
	@Override
	public String getName() {
		return "Help";
	}
	
	@Override
	public int getCategory() {
		return 1;
	}
	
	@Override
	public String getDescription() {
		return "Displays all commands";
	}
	
	@Override
	public String getExample() {
		return "Two uses:\n"
		+ Config.prefix + "" + getName() + " Displays all commands.\n"
		+ Config.prefix + "" + getName() + " (command) provides further information on a command\n"
		+ "\nExample:\n" + Config.prefix + "" + getName() + " clear will display futher information on clear";
	}
	
	/**
	 * Returns a string that displays each argument for a command
	 */
	public String printArgs(String[] args) {
		String message = "";
		for (int i = 0; i < args.length; i++) {
			message += "(" + args[i] + ") ";
		}
		return message;
	}
	
	/**
	 * Creates a string that displays all command names and their descriptions for a certain category
	 * @param commands ArrayList that holds all commands of a certain category
	 * @return message that displays all command names and their descriptions
	 */
	public String printMessage(ArrayList<Command> commands) {
		StringBuilder message = new StringBuilder("```\n");
		for (Command command : commands) {
			String argsMsg = printArgs(command.getArgs());
			String desc = command.getDescription();
			String fDesc = String.format("%" + (desc.length() + 25 - command.getName().length() - argsMsg.length()) + "s", desc);
			
			message.append(Config.prefix + "" + command.getName() + " " + argsMsg + fDesc + "\n");
		}
		message.append("\n```");
		return message.toString();
	}
}