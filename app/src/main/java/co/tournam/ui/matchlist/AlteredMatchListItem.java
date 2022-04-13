package co.tournam.ui.matchlist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
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

public class AlteredMatchListItem extends LinearLayout {

    private List<SummaryMatchListItemTeam> teamItems;
    private MatchModel match;
    private TextView timeText;

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

        setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("matchid", match.getId());
            Intent intent = new Intent(context, MatchDetailActivity.class);
            intent.putExtras(bundle);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });

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

    public List<SummaryMatchListItemTeam> getTeamItems() {
        return teamItems;
    }
}
