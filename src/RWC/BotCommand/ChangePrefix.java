package RWC.BotCommand;


import RWC.Bot.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.internal.utils.PermissionUtil;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

/**
 * Changes the bot prefix to a designated one
 * Can only be used by a user with the administrator permission
 * @author Rose Griffin
 *
 */
public class ChangePrefix extends Command{

	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		
		if(args[0].equalsIgnoreCase(Config.prefix + getName())) {
			
			//Check to see if user has permission to use command
			if (!PermissionUtil.checkPermission(event.getChannel(),event.getMember(), Permission.ADMINISTRATOR)) {
				event.getChannel().sendMessage("You do not have permission to use this command").queue();
			} else if (args.length == 2) {
				Config.setPrefix(args[1]);
				event.getChannel().sendMessage("Prefix successfully changed to " + Config.prefix + "\nTry " + Config.prefix + "Help").queue();
			} else {
				EmbedBuilder change = new EmbedBuilder();
				
				change.setTitle("⚠Syntax Error");
				change.setDescription("Please tell me what to change the prefix to");
				change.addField("Example", Config.prefix + "" + "changePrefix r!",false);
				change.setColor(0xeb3434);
				
				event.getChannel().sendMessage(change.build()).queue();
				change.clear();
			}
		}
	}

	@Override
	public String getName() {
		return "ChangePrefix";
	}
	
	@Override
	public int getCategory() {
		return 0;
	}
	
	@Override
	public String getArgs() {
		return "[prefix]";
	}

	@Override
	public String getDescription() {
		return "Changes the bot prefix";
	}

	@Override
	public String getExample() {
		return "Argument " + getArgs() + ": The prefix to be changed into"
				+ "\nThis command changes the bot prefix"
				+ "\n\nExample:\n" + Config.prefix + "" + getName() + " r! will change the prefix to r!";
	}





}
