package co.tournam.ui.teamscore;

import android.content.Context;

import java.util.Arrays;

import co.tournam.models.MatchModel;
import co.tournam.ui.table.Score;

public class TeamScore extends AbstractTeamScore {
    public TeamScore(Context context, MatchModel match) {
        super(context, match);

        build(context);
    }

    public void build(Context context) {

        buildContents(context);
    }

    public void buildContents(Context context) {
        TeamScoreIconItem team1 = new TeamScoreIconItem(context, this.match.getTeams().get(0));
        Score score = new Score(context, Arrays.asList(
                this.match.getScore(this.match.getKeys().get(0)),
                this.match.getScore(this.match.getKeys().get(1))));
        TeamScoreIconItem team2 = new TeamScoreIconItem(context, this.match.getTeams().get(1));

        this.addView(team1);
        this.addView(score);
        this.addView(team2);
    }

}