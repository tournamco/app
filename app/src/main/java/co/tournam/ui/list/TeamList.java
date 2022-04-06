package co.tournam.ui.list;

import android.content.Context;
import android.widget.LinearLayout;

import java.util.List;

public class TeamList extends LinearLayout {

    private List<String> teamIDs;
    private String buttonText;

    public TeamList(Context context, List<String> teamIDs, String buttonText) {
        super(context);

        this.teamIDs = teamIDs;
        this.buttonText = buttonText;
    }

    public void build(Context context) {
        setOrientation(LinearLayout.VERTICAL);

        buildContents(context);
    }

    public void buildContents(Context context) {

        for (String teamID : teamIDs) {
            TeamListRow row = new TeamListRow(context, teamID, buttonText);
            this.addView(row);
        }

    }

    public List<String> getTeams() {
        return this.teamIDs;
    }
}
