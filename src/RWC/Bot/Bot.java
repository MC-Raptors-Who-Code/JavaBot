package RWC.Bot;

import javax.security.auth.login.LoginException;

import RWC.BotCommand.CommandManager;
import RWC.BotEvent.AddRole;
import RWC.BotEvent.GuildMemberJoin;
import RWC.BotEvent.GuildMemberLeave;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

public class Bot {
	
	public static JDA jda;
	
	//Main method
	//Test
	public static void main(String[]args) throws LoginException{
		jda = JDABuilder.createDefault(Config.TOKEN).build();
		jda.getPresence().setStatus(OnlineStatus.IDLE);
		jda.getPresence().setActivity(Activity.watching("One Punch Man"));
		jda.addEventListener(new CommandManager());
		jda.addEventListener(new GuildMemberJoin());
		jda.addEventListener(new GuildMemberLeave());
		jda.addEventListener(new AddRole());
	}
}
