package co.tournam.ui.team_members;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import co.tournam.models.TeamModel;
import co.tournam.schedule.R;

public class TeamMembers extends AbstractTeamMembers {

    private TextView teamOne;
    private TextView teamTwo;

    /**
     * Constructor for TeamMembers
     *
     * @param context the current context.
     * @param teams   the list of TeamModels.
     */
    public TeamMembers(Context context, List<TeamModel> teams) {
        super(context, teams);

        build(context);
    }

    /**
     * Build method provides the option of modifying the layout before building
     * its contents
     *
     * @param context the current context
     */
    private void build(Context context) {
        setOrientation(LinearLayout.VERTICAL);
        setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        buildContents(context);
    }

    /**
     * the buildContents method inflates the layout and builds/initiates the full
     * UI-element.
     *
     * @param context the current context.
     */
    private void buildContents(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.team_member_total, this, true);

        teamOne = (TextView) findViewById(R.id.teamOne);
        LinearLayout leftTeam = (LinearLayout) findViewById(R.id.leftTeam);
        new TeamMembersItem(context, this.teams.get(0), true, leftTeam);
        teamOne.setText(this.teams.get(0).getName());

        teamTwo = (TextView) findViewById(R.id.teamTwo);
        LinearLayout rightTeam = (LinearLayout) findViewById(R.id.rightTeam);
        new TeamMembersItem(context, this.teams.get(1), false, rightTeam);
        teamTwo.setText(this.teams.get(1).getName());
    }
}
