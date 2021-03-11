package RWC.BotEvent;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * Adds a role to a member when they react to a designated message
 * @author Rose Griffin
 *
 */
public class AddRole extends ListenerAdapter {
	
	//Id of the message to check for reactions
	String messageID = "";
	//Correct Emoji
	String emoji = "👍";
	//Id of the role to add
	long roleID = 0L;
	
	@Override
	public void onMessageReactionAdd(MessageReactionAddEvent event) {
		
		//Check if the message reacted on is the designated message and that the correct emoji was used
		if (event.getMessageId().equals(messageID) && event.getReactionEmote().getEmoji().equals(emoji)) {
			Member member = event.getMember();
			Role role = event.getJDA().getRoleById(roleID);
			
			event.getGuild().addRoleToMember(member, role).queue();
		}
	}
}
