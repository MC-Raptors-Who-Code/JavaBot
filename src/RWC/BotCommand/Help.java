package RWC.BotCommand;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

/**
 * Displays name and description of each command in an embed
 * 
 * @author Rose Griffin
 *
 */
public class Help extends AbstractCommand{
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		
		AbstractCommand[] commands = {new Help(), new Meet(), new Clear()};
		EmbedBuilder help = new EmbedBuilder();
		String[] args=event.getMessage().getContentRaw().split("\\s+");
		
		if(args.length == 1 && args[0].equalsIgnoreCase(PREFIX + getName())) {

			StringBuilder message = new StringBuilder("```\n");
			
			//Builds message
			for (AbstractCommand command: commands) {
				message.append(PREFIX + "" + command.getName() + " " + command.getArgs() 
				+ String.format("%" + (command.getDescription().length() + 20 - command.getName().length() - command.getArgs().length()) 
				+ "s", command.getDescription()) + "\n");
			}
			message.append("\n```");
			
			//Builds embed
			help.setTitle("Commands");
			help.addField("General:", message.toString(),true);
			help.setColor(0x592e8e);
			help.setFooter("Here you go! For further information on a command type " + PREFIX 
					+ "" + getName() + " [command]" ,event.getMember().getUser().getAvatarUrl());
			event.getChannel().sendMessage(help.build()).queue();
			
		} else if (args.length > 1 && args[0].equalsIgnoreCase(PREFIX + getName())) {
			
			try {
				
				AbstractCommand command = findCommand(commands, args[1]);
				
				//Builds embed
				help.setTitle("Help " + "" + PREFIX + "" + command.getName());
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
	public String getDescription() {
		return "Displays all commands";
	}
	
	@Override
	public String getExample() {
		return "Two uses:\n"
		+ PREFIX + "" + getName() + " Displays all commands.\n"
		+ PREFIX + "" + getName() + " [command] provides further information on a command\n"
		+ "\nExample:\n" + PREFIX + "" + getName() + " clear will display futher information on clear";
	}
	
	/**
	 * Finds the corresponding command based on the string passed
	 * 
	 * @param commands	Array containing all commands
	 * @param name	Name of the command
	 * @return	The corresponding command
	 * @throws IllegalArgumentException	If the string passed does not match a command name
	 */
	private AbstractCommand findCommand(AbstractCommand[] commands, String name) throws IllegalArgumentException {
		//Search for command and return it
		for (AbstractCommand command: commands) {
			if ((command.getName()).equalsIgnoreCase(name))
				return command;
		}
		//Throw an exception if the argument passed is not a valid command
		throw new IllegalArgumentException();
	}

}