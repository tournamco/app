package co.tournam.ui.list;

import android.content.Context;
import android.widget.LinearLayout;

import java.util.List;

import co.tournam.models.TeamModel;

public class TeamList extends LinearLayout {

    private List<TeamModel> teams;
    private String buttonText;
    private TeamListActionListener listener;
    private Context context;

    public TeamList(Context context, List<TeamModel> teams, String buttonText, TeamListActionListener listener) {
        super(context);

        this.listener = listener;
        this.teams = teams;
        this.buttonText = buttonText;
        this.context = context;
    }

    public void build(Context context) {
        setOrientation(LinearLayout.VERTICAL);

        buildContents(context);
    }

    public void buildContents(Context context) {
        for (TeamModel team : teams) {
            this.addTeam(team);
        }
    }

    public void addTeam(TeamModel team) {
        TeamListRow row = new TeamListRow(context, team, buttonText, listener);
        this.addView(row);
    }

    public interface TeamListActionListener {
        void onTeamSelected(TeamModel team);
    }
}
