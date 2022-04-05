package co.tournam.ui.matchlist;

import android.content.Context;
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
import co.tournam.schedule.R;

public class AlteredMatchListItem extends LinearLayout {

    private List<SummaryMatchListItemTeam> teamItems;
    private MatchModel match;
    private TextView timeText;

    private View colorView;

    public AlteredMatchListItem(Context context, MatchModel match) {
        super(context);

        this.match = match;

        build(context);
    }

    private void build(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);

        buildContents(context);
    }

    private void buildContents(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.match_list_item_altered, this, true);

        colorView = (View)findViewById(R.id.color_altered);
        setColor(match.getTournament().getColor());

        timeText = (TextView)findViewById(R.id.time_altered);
        setTime(match.getStartDate());

        LinearLayout teams = (LinearLayout) findViewById(R.id.teamlist_altered);
        teamItems = new ArrayList<>();

        for(String key : match.getTeams().keySet()) {
            TeamModel team = match.getTeams().get(key);
            SummaryMatchListItemTeam teamItem = new SummaryMatchListItemTeam(context, team, match.getScore(key), match.isWinner(team));
            teamItems.add(teamItem);
            teams.addView(teamItem);
        }
    }

    private void setTime(LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy HH:mm");
        timeText.setText(time.format(formatter));
    }

    public void setColor(int color) {
        colorView.setBackgroundColor(color);
    }

    public List<SummaryMatchListItemTeam> getTeamItems() {
        return teamItems;
    }
}
