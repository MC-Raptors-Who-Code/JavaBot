package RWC.Bot;

import javax.security.auth.login.LoginException;

import RWC.BotCommand.ChangePrefix;
import RWC.BotCommand.Clear;
import RWC.BotCommand.Help;
import RWC.BotCommand.Meet;
import RWC.BotEvent.GuildMemberJoin;
import RWC.BotEvent.GuildMemberLeave;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

public class Bot {
	
	public static JDA jda;
	
	//Main method

	public static void main(String[]args)throws LoginException{
		jda = JDABuilder.createDefault(Config.TOKEN).build();
		jda.getPresence().setStatus(OnlineStatus.IDLE);
		jda.getPresence().setActivity(Activity.watching("One Punch Man"));
		
		jda.addEventListener(new Help());
		jda.addEventListener(new Meet());
		jda.addEventListener(new Clear());
		jda.addEventListener(new ChangePrefix());
		jda.addEventListener(new GuildMemberJoin());
		jda.addEventListener(new GuildMemberLeave());
	}

}
