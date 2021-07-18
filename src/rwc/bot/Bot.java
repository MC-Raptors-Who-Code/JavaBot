package rwc.bot;

import java.io.IOException;
import java.security.GeneralSecurityException;
import com.google.api.services.sheets.v4.Sheets;
import rwc.botcommand.CommandManager;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class Bot {
	
	public static Sheets service;
	public static final String PREFIX = "!";
	private static String token;
	
	//Main method
	public static void main(String[] args) throws GeneralSecurityException, IOException {
		
		//Set bot token
		token = args[0];
		
		JDABuilder.createDefault(token)
				.enableIntents(GatewayIntent.GUILD_MEMBERS)
				.setMemberCachePolicy(MemberCachePolicy.ALL)
				.addEventListeners(new CommandManager())
				.setStatus(OnlineStatus.IDLE)
				.setActivity(Activity.watching("One Punch Man"))
				.build();
		
		service = SheetsUtil.getService();
	}
}
