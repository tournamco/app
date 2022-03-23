package co.tournam.team_members;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import co.tournam.models.TeamModel;
import co.tournam.schedule.R;

public class TeamMembers extends AbstractTeamMembers{

    private TextView teamOne;
    private TextView teamTwo;

    public TeamMembers(Context context, List<TeamModel> teams) {
        super(context, teams);

        build(context);
    }

    private void build(Context context) { buildContents(context); }

    private void buildContents(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.team_member_total, this, true);

        boolean isItTeamOne = true;
        for(TeamModel team : this.teams) {
            if(isItTeamOne) {
                teamOne = (TextView)findViewById(R.id.teamOne);
                LinearLayout leftTeam = (LinearLayout) findViewById(R.id.leftTeam);
                TeamMembersItem item = new TeamMembersItem(context, team, isItTeamOne, leftTeam);
                setName(teamOne, team.getName());
                this.addView(item);
            } else {
                teamTwo = (TextView)findViewById(R.id.teamTwo);
                LinearLayout rightTeam = (LinearLayout) findViewById(R.id.rightTeam);
                TeamMembersItem item = new TeamMembersItem(context, team, isItTeamOne, rightTeam);
                setName(teamTwo, team.getName());
                this.addView(item);
            }
            isItTeamOne = false;
        }
    }
    private void setName(TextView textview, String name) { textview.setText(name);}
}
