package rwc.botevent;

import java.util.Random;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildMemberJoin extends ListenerAdapter{
	String[] messages = {
			"[member] joined. You must construct additional pylons.",
			"Never gonna give [member] up. Never let [member] down!",
			"Hey! Listen! [member] has joined!",
			"Ha! [member] has joined! You activated my trap card!",
			"We've been expecting you, [member].",
			"It's dangerous to go alone, take [member]!",
			"Swoooosh. [member] just landed.",
			"Brace yourselves. [member] just joined the server.",
			"A wild [member] appeared."
		};
	
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
		Random rand =new Random();
		int number=rand.nextInt(messages.length);
		EmbedBuilder join=new EmbedBuilder();
		join.setColor(0xdceb34);
		join.setDescription(messages[number].replace("[member]", event.getMember().getAsMention()));
		
		event.getGuild().getDefaultChannel().sendMessageEmbeds(join.build()).queue();
		//Add role
		event.getGuild().modifyMemberRoles(event.getMember(), event.getGuild().getRolesByName("Member", true)).complete();
	}

}
