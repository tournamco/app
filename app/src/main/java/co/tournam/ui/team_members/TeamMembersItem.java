package co.tournam.ui.team_members;

import android.content.Context;
import android.widget.LinearLayout;

import java.util.List;

import co.tournam.models.TeamModel;
import co.tournam.models.UserModel;

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
