package RWC.BotCommand;

import RWC.Bot.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

//Hey my name is Franco. I am making some modifications to the code.

public class Meet extends Command {
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent event, String[] args) {
		EmbedBuilder meet = new EmbedBuilder();
		meet.setTitle("📅 Meeting Schedule");
		meet.setDescription("Weekly meeting schedule");
		meet.addField("THURSDAY","5-6 PM",false);
		meet.setColor(0x3452eb);
		meet.setFooter("Here you go!",event.getMember().getUser().getAvatarUrl());
		
		//event.getChannel().sendTyping().queue();
		event.getChannel().sendMessage(meet.build()).queue();
		meet.clear();
	}

	@Override
	public String getName() {
		return "Meet";
	}
	
	@Override
	public int getCategory() {
		return 1;
	}
	
	@Override
	public String getDescription() {
		return "Displays club meeting times";
	}

	@Override
	public String getExample() {
		return getDescription() + "\nExample: " + Config.PREFIX + "" + getName();
	}



}