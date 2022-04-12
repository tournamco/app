package co.tournam.ui.list;

import android.content.Context;
import android.widget.LinearLayout;

import java.util.List;

import androidx.appcompat.widget.LinearLayoutCompat;
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

        build(context);
    }

    public void build(Context context) {
        setOrientation(LinearLayout.VERTICAL);
        setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT));

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
