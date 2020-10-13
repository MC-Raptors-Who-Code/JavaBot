package RWC.BotEvent;

import java.util.Random;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildMemberLeave extends ListenerAdapter{
	String[] messages = {
			"[member] has left us. What shall we do😪",
			"No.[member] don't leave us💔"
		};
	
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
		Random rand =new Random();
		int number=rand.nextInt(messages.length);
		EmbedBuilder leave=new EmbedBuilder();
		leave.setColor(0x3003fc);
		leave.setDescription(messages[number].replace("[member]", event.getMember().getAsMention()));
		
		event.getGuild().getDefaultChannel().sendMessage(leave.build()).queue();
		//Add role
		event.getGuild().modifyMemberRoles(event.getMember(), event.getGuild().getRolesByName("Member", true)).complete();
	}
}
