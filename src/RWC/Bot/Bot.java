package RWC.Bot;

import javax.security.auth.login.LoginException;

import RWC.BotCommand.Clear;
import RWC.BotCommand.Meet;
import RWC.BotEvent.GuildMemberJoin;
import RWC.BotEvent.GuildMemberLeave;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

public class Bot {
	public static JDA jda;
	public static String prefix="!";
	
	//Main method
	public static void main(String[]args)throws LoginException{
		jda = JDABuilder.createDefault("TOKEN").build();
		jda.getPresence().setStatus(OnlineStatus.IDLE);
		jda.getPresence().setActivity(Activity.watching("One Punch Man"));
		
		jda.addEventListener(new Meet());
		jda.addEventListener(new Clear());
		jda.addEventListener(new GuildMemberJoin());
		jda.addEventListener(new GuildMemberLeave());
	}
}
