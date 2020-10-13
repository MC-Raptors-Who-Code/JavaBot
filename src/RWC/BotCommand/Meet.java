package RWC.BotCommand;

import RWC.Bot.Bot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Meet extends ListenerAdapter{
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String[] args=event.getMessage().getContentRaw().split("\\s+");
		
		if(args[0].equalsIgnoreCase(Bot.prefix+"Meet")) {
			EmbedBuilder Meet=new EmbedBuilder();
			Meet.setTitle("📅 Meeting Schedule");
			Meet.setDescription("Weekly meeting schedule");
			Meet.addField("TUESDAY & THURSDAY","5-6 PM",false);
			Meet.setColor(0x3452eb);
			Meet.setFooter("Here you go!",event.getMember().getUser().getAvatarUrl());

			
			//event.getChannel().sendTyping().queue();
			event.getChannel().sendMessage(Meet.build()).queue();
			Meet.clear();
			
		}
	}

}
