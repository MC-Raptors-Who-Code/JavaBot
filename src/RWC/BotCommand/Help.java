package RWC.BotCommand;

import RWC.Bot.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

/**
 * Displays name and description of each command in an embed
 * 
 * @author Rose Griffin
 *
 */
public class Help extends Command {
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		
		Command[] commands = {new Help(), new Meet(), new Clear(), new ChangePrefix()};
		EmbedBuilder help = new EmbedBuilder();
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		
		if(args.length == 1 && args[0].equalsIgnoreCase(Config.prefix + getName())) {

			StringBuilder message = new StringBuilder("```\n");
			
			//Builds message
			for (Command command: commands) {
				message.append(Config.prefix + "" + command.getName() + " " + command.getArgs() 
				+ String.format("%" + (command.getDescription().length() + 25 - command.getName().length() - command.getArgs().length()) 
				+ "s", command.getDescription()) + "\n");
			}
			message.append("\n```");
			
			//Builds embed
			help.setTitle("Commands");
			help.addField("General:", message.toString(),false);
			help.setColor(0x592e8e);
			help.setFooter("Here you go! For further information on a command type " + Config.prefix 
					+ "" + getName() + " [command]" ,event.getMember().getUser().getAvatarUrl());
			event.getChannel().sendMessage(help.build()).queue();
			
		} else if (args.length > 1 && args[0].equalsIgnoreCase(Config.prefix + getName())) {
			
			try {
				
				Command command = findCommand(commands, args[1]);
				
				//Builds embed
				help.setTitle("Help " + "" + Config.prefix + "" + command.getName());
				help.setDescription(command.getExample());
				help.setColor(0x592e8e);
				help.setFooter("Here you go!",event.getMember().getUser().getAvatarUrl());
				
				event.getChannel().sendMessage(help.build()).queue();
			} catch (Exception e) {
				
				//Builds embed
				help.setTitle("⚠Syntax Error");
				help.setDescription(args[1] + " is not a valid command");
				help.addField("Example","r!help clear",false);
				help.setColor(0xeb3434);
				
				event.getChannel().sendMessage(help.build()).queue();
				help.clear();
			}
		}
	}
	
	@Override
	public String getName() {
		return "Help";
	}
	
	@Override
	public int getCategory() {
		return 0;
	}
	
	@Override
	public String getDescription() {
		return "Displays all commands";
	}
	
	@Override
	public String getExample() {
		return "Two uses:\n"
		+ Config.prefix + "" + getName() + " Displays all commands.\n"
		+ Config.prefix + "" + getName() + " [command] provides further information on a command\n"
		+ "\nExample:\n" + Config.prefix + "" + getName() + " clear will display futher information on clear";
	}
	
	/**
	 * Searches for the corresponding command based on the string passed
	 * NOTE: The search algorithm must be changed in the future when more commands are added.
	 * 
	 * @param commands	Array containing all commands
	 * @param name	Name of the command
	 * @return	The corresponding command
	 * @throws IllegalArgumentException	If the string passed does not match a command name
	 */
	private Command findCommand(Command[] commands, String name) throws IllegalArgumentException {
		//Search for command and return it
		for (Command command: commands) {
			if ((command.getName()).equalsIgnoreCase(name))
				return command;
		}
		//Throw an exception if the argument passed is not a valid command
		throw new IllegalArgumentException();
	}
}