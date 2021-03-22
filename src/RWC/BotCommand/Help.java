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
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent event, String[] args) {
		
		commands = CommandManager.getCommands();
		ArrayList<Command> commandList = new ArrayList<Command>(commands.values());
		EmbedBuilder help = new EmbedBuilder();
		
		if(args.length == 1) {
			
			StringBuilder message = new StringBuilder("```\n");
			
			//Builds message
			for (Command command : commandList) {
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
			
		} else if (args.length == 2) {
			
			try {
				Command command = commands.get(args[1]);
				
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
				help.addField("Example", Config.prefix + "help clear",false);
				help.setColor(0xeb3434);
				
				event.getChannel().sendMessage(help.build()).queue();
				help.clear();
			}
		} else {
			//Builds embed
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
}