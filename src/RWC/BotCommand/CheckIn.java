package RWC.BotCommand;

import java.io.IOException;
import java.util.List;

import com.google.api.services.sheets.v4.model.ValueRange;

import RWC.Bot.Bot;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

/**
 * Command that adds the member role to a person in the server through completion of a form
 * 
 * @author Rose Griffin
 *
 */
public class CheckIn extends Command {
	
	private static final String spreadsheetId = "";
	private static final long roleId = 0L; //Id of member role
	private static final String RANGE = ""; //Cell range
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event, String[] args) {
		//Get the values of the cells within the designated range and store them in a 2d list
		ValueRange response = null;
		try {
			response = Bot.service.spreadsheets().values().get(spreadsheetId, RANGE).execute();
		} catch (IOException e) {
			System.out.println("Unable to get values from spreadsheet");
			return;
		}
        List<List<Object>> values = response.getValues();
        
        //Traverse values and add member role to provided discord tag if possible
        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
        } else {
        	Role role = event.getJDA().getRoleById(roleId);
            for (List row : values) {
            	Member member = event.getGuild().getMemberByTag((String) row.get(1));
                /*
                 * Check if member can be given the member role, and if so,
                 * give them the role and change their nickname to their first name.
                 * This fails if they have not been cached, their tag is wrong, or they have already been given the role.
                 */
                if (member != null && !member.getRoles().contains(role)) {
                	event.getGuild().addRoleToMember(member, role).queue();
	                event.getGuild().modifyNickname(member, (String) row.get(0)).queue();
	                event.getChannel().sendMessage(member.getAsMention() + " is now a member!").queue();
                } else {
                	System.out.println("Unable to give " + row.get(1) + " the member role.");
                }
            }
        }
	}

	@Override
	public String getName() {
		return "CheckIn";
	}

	@Override
	public int getCategory() {
		return 1;
	}

	@Override
	public String getDescription() {
		return "Get member role after form completion";
	}

	@Override
	public String getExample() {
		return getDescription() + "\nExample: " + Bot.PREFIX + "" + getName();
	}
	

}
