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

    /**
     * The constructor for the TeamList class.
     *
     * @param context    the current context.
     * @param teams      the list of teammodels.
     * @param buttonText the name of a button.
     * @param listener   the listener for this class.
     */
    public TeamList(Context context, List<TeamModel> teams, String buttonText, TeamListActionListener listener) {
        super(context);

        this.listener = listener;
        this.teams = teams;
        this.buttonText = buttonText;
        this.context = context;

        build(context);
    }

    /**
     * Build method provides the option of modifying the layout before building
     * its contents
     *
     * @param context the current context
     */
    public void build(Context context) {
        setOrientation(LinearLayout.VERTICAL);
        setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT));

        buildContents(context);
    }

    /**
     * the buildContents method inflates the layout and builds/initiates the full
     * UI-element.
     *
     * @param context the current context.
     */
    public void buildContents(Context context) {
        for (TeamModel team : teams) {
            this.addTeam(team);
        }
    }

    /**
     * A function for adding a team
     *
     * @param team a teammodel
     */
    public void addTeam(TeamModel team) {
        TeamListRow row = new TeamListRow(context, team, buttonText, listener);
        this.addView(row);
    }

    /**
     * The listener for this class.
     */
    public interface TeamListActionListener {
        void onTeamSelected(TeamModel team);
    }
}
