package co.tournam.team_members;

import android.content.Context;
import android.widget.LinearLayout;

import java.util.List;

import co.tournam.models.TeamModel;

public abstract class AbstractTeamMembers extends LinearLayout {

    protected List<TeamModel> teams;

    public AbstractTeamMembers(Context context, List<TeamModel> teams) {
        super(context);

        this.teams = teams;

        build(context);
    }

    private void build(Context context) { setOrientation(LinearLayout.VERTICAL); }
}
