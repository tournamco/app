package co.tournam.ui.team_members;

import android.content.Context;
import android.view.Gravity;
import android.widget.LinearLayout;

import java.util.List;

import co.tournam.models.TeamModel;
import co.tournam.models.UserModel;

public class TeamMembersItem extends LinearLayout {

    private List<TeamMembers> teamMemberItems;
    private TeamModel team;
    private Boolean isItTeamOne;
    private LinearLayout specificTeam;

    public TeamMembersItem(Context context, TeamModel team, Boolean isItTeamOne, LinearLayout specificTeam) {
        super(context);

        this.team = team;
        this.isItTeamOne = isItTeamOne;
        this.specificTeam = specificTeam;

        build(context);
    }

    private void build(Context context) {
        setOrientation(LinearLayout.VERTICAL);
        setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 0));
        setGravity(Gravity.RIGHT);

        if(team == null){
            return;
        }

        for(UserModel user : team.getMembers()) {
            TeamMembersItemUser teamMember = new TeamMembersItemUser(context, user, isItTeamOne);
            specificTeam.addView(teamMember);
        }
    }
}
