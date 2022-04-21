package co.tournam.ui.matchlist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import co.tournam.models.MatchModel;
import co.tournam.models.TeamModel;
import co.tournam.schedule.MatchDetailActivity;
import co.tournam.schedule.R;

public class SummaryMatchListItem extends LinearLayout {

    private List<SummaryMatchListItemTeam> teamItems;
    private MatchModel match;
    private TextView timeText;
    private TextView matchnameText;

    private View colorView;

    /**
     * Constructor for SummaryMatchListItem.
     *
     * @param context the current context
     * @param match   the matchModel being added to the list.
     */
    public SummaryMatchListItem(Context context, MatchModel match) {
        super(context);

        this.match = match;

        build(context);
    }

    /**
     * Build method provides the option of modifying the layout before building
     * its contents
     *
     * @param context the current context
     */
    private void build(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);

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
        inflater.inflate(R.layout.match_list_item, this, true);

        setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("matchid", match.getId());
            Intent intent = new Intent(context, MatchDetailActivity.class);
            intent.putExtras(bundle);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });

        colorView = (View) findViewById(R.id.color);
        setColor(match.getTournament().getColor());

        View color2View = (View) findViewById(R.id.color2);
        color2View.setBackgroundColor(match.getTournament().getColor());

        timeText = (TextView) findViewById(R.id.time);
        setTime(match.getStartDate());

        matchnameText = (TextView) findViewById(R.id.matchname);
        setMatchname(match);

        LinearLayout teams = (LinearLayout) findViewById(R.id.teamlist);
        teamItems = new ArrayList<>();

        for (String key : match.getTeams().keySet()) {
            TeamModel team = match.getTeams().get(key);
            SummaryMatchListItemTeam teamItem = new SummaryMatchListItemTeam(context, team, match.getScore(key), match.isWinner(team));
            teamItems.add(teamItem);
            teams.addView(teamItem);
        }
    }

    /**
     * A function to set a time
     *
     * @param time the time to be set.
     */
    private void setTime(LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy HH:mm");
        timeText.setText(time.format(formatter));
    }

    /**
     * A function to set the matchname
     *
     * @param match the match to be set.
     */
    private void setMatchname(MatchModel match) {
        matchnameText.setText(match.getName());
    }

    /**
     * A function to set the color of a match
     *
     * @param color the color to be set.
     */
    public void setColor(int color) {
        colorView.setBackgroundColor(color);
    }

    /**
     * Getter for teamItems.
     *
     * @return a list of teamItems.
     */
    public List<SummaryMatchListItemTeam> getTeamItems() {
        return teamItems;
    }
}
