package co.tournam.ui.teamscore;

import android.content.Context;
import android.service.autofill.FieldClassification;

import java.util.ArrayList;

import co.tournam.models.MatchModel;
import co.tournam.models.stage.PoolsStageModel;
import co.tournam.table.Score;
import co.tournam.ui.stagelist.AbstractStageList;

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
        Score score = new Score(context, this.match);
        TeamScoreIconItem team2 = new TeamScoreIconItem(context, this.match.getTeams().get(1));

        this.addView(team1);
        this.addView(score);
        this.addView(team2);
    }

}