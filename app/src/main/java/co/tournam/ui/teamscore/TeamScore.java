package co.tournam.ui.teamscore;

import android.content.Context;

import java.util.Arrays;

import co.tournam.models.MatchModel;
import co.tournam.ui.table.Score;

public class TeamScore extends AbstractTeamScore {

    /**
     * The constructor for TeamScore
     *
     * @param context the current context.
     * @param match   the match of the score.
     */
    public TeamScore(Context context, MatchModel match) {
        super(context, match);

        build(context);
    }

    /**
     * Build method provides the option of modifying the layout before building
     * its contents
     *
     * @param context the current context
     */
    public void build(Context context) {

        buildContents(context);
    }

    /**
     * the buildContents method inflates the layout and builds/initiates the full
     * UI-element.
     *
     * @param context the current context.
     */
    public void buildContents(Context context) {
        TeamScoreIconItem team1 = new TeamScoreIconItem(context, this.match.getTeams().get(this.match.getKeys().get(0)));
        Score score = new Score(context, Arrays.asList(
                this.match.getScore(this.match.getKeys().get(0)),
                this.match.getScore(this.match.getKeys().get(1))));
        TeamScoreIconItem team2 = new TeamScoreIconItem(context, this.match.getTeams().get(this.match.getKeys().get(1)));

        this.addView(team1);
        this.addView(score);
        this.addView(team2);
    }

}