package co.tournam.ui.team_members;

import android.content.Context;
import android.widget.LinearLayout;

import java.util.List;

import co.tournam.models.TeamModel;

public abstract class AbstractTeamMembers extends LinearLayout {

    protected List<TeamModel> teams;

    /**
     * The Constructor for AbstractTeamMembers
     *
     * @param context the current context.
     * @param teams   the list of TeamModels.
     */
    public AbstractTeamMembers(Context context, List<TeamModel> teams) {
        super(context);

        this.teams = teams;

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
    }
}
