package RWC.BotCommand;

import java.util.List;

import RWC.Bot.Bot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * Clear class for the clear command
 * @author steum
 *
 */
public class Clear extends ListenerAdapter{
	/**
	 * Method to run event of clear command
	 */
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		
		//command entered by user
		String[] args=event.getMessage().getContentRaw().split("\\s+");
		//checking command syntax
		if(args[0].equalsIgnoreCase(Bot.prefix+"clear")) {
			//Embed to print messages
			EmbedBuilder clear=new EmbedBuilder();
			
			//check if command has the right number of arguments
			if(args.length<2) {
				
				clear.setTitle("⚠Syntax Error");
				clear.setDescription("Please tell me how many messages I should delete");
				clear.addField("Example","$clear 50",false);
				clear.setColor(0xeb3434);
				//Meet.setFooter("Here you go!",event.getMember().getUser().getAvatarUrl());
				event.getChannel().sendMessage(clear.build()).queue();
				clear.clear();
			}
			else {
				
				// try & catch blocks because discord doesn't allow to delete more than 100 messages or messages older than 2 weeks
				try {
				// retrieve message of the channel where command was written	
				List<Message>messages=event.getChannel().getHistory().retrievePast(Integer.parseInt(args[1])).complete();
				event.getChannel().deleteMessages(messages).queue();
				
				//success in deletion
				clear.setTitle("✅SUCCESS");
				clear.setDescription("Successfully deleted. Hooray!🎉");
				clear.setColor(0x34eb6e);
				//Meet.setFooter("Here you go!",event.getMember().getUser().getAvatarUrl());
				event.getChannel().sendMessage(clear.build()).queue();
				clear.clear();
				
				}
				catch(IllegalArgumentException e) {
					//Embeds to print messages in case of error (more than 100 messages or > 2 weeks old)
					if(e.toString().startsWith("java.lang.IllegalArgumentException: Message retrieval")) {
						EmbedBuilder error=new EmbedBuilder();
						error.setTitle("🛑Too many messages");
						error.setDescription("I can't delete more than 100 messages. Sorry!😁");
						error.setColor(0xeb3434);
						event.getChannel().sendMessage(error.build()).queue();
						error.clear();
					}
					else {
						EmbedBuilder error=new EmbedBuilder();
						error.setTitle("❌Old messages");
						error.setDescription("Either you just asked me to delete a 2 weeks or older message or there are no messages left for me to delete. Hmm?🤔");
						error.setColor(0xeb3434);
						event.getChannel().sendMessage(error.build()).queue();
						error.clear();
					}
				}
			}
			
			
		}
	}

}
