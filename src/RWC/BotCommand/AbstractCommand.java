package RWC.BotCommand;

import RWC.Bot.Config;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * Abstract class extended by all commands
 * 
 * @author Rose Griffin
 *
 */
abstract class AbstractCommand extends ListenerAdapter {
	
	public String PREFIX = Config.PREFIX;
	
	public abstract void onGuildMessageReceived(GuildMessageReceivedEvent event);
	
	/**
	 * Returns name of a command
	 * For instance: Help
	 */
	public abstract String getName();
	
	/**
	 * Returns the arguments of a command
	 * If the command does not have arguments, return an empty string
	 */
	public String getArgs() {
		return "";
	}
	
	/**
	 * Returns a String that describes what the command does
	 */
	public abstract String getDescription();
	
	/**
	 * Returns a message that further explains a command's functions, its argument(s), and provides
	 * examples on usage.
	 */
	public abstract String getExample();
	
}