package co.tournam.team_members;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import co.tournam.models.TeamModel;
import co.tournam.models.UserModel;
import co.tournam.schedule.R;

public class TeamMembersItem extends LinearLayout {

    private List<TeamMembers> teamMemberItems;
    private TeamModel team;
    private Boolean isItTeamOne;
    private LinearLayout specificTeam;



    public TeamMembersItem(Context context, TeamModel team, Boolean isItTeamOne, LinearLayout layOut) {
        super(context);

        this.team = team;
        this.isItTeamOne = isItTeamOne;
        this.specificTeam = layOut;

        build(context);
    }

    private void build(Context context) {

        if(isItTeamOne) {
            for(UserModel user: team.getMembers()) {
                TeamMembersItemUser teamMember = new TeamMembersItemUser(context, user, isItTeamOne);
                specificTeam.addView(teamMember);
            }
        } else {
            for(UserModel user: team.getMembers()) {
                TeamMembersItemUser teamMember = new TeamMembersItemUser(context, user, isItTeamOne);
                specificTeam.addView(teamMember);
            }
        }
    }
}
