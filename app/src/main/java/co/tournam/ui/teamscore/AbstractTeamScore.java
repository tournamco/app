package co.tournam.ui.teamscore;

import android.content.Context;
import android.widget.LinearLayout;

import co.tournam.models.MatchModel;


public abstract class AbstractTeamScore extends LinearLayout {

    protected MatchModel match;

    /**
     * Constructor for AbstractTeamScore
     *
     * @param context the current context.
     * @param match   the matchModel used for the score.
     */
    public AbstractTeamScore(Context context, MatchModel match) {
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
    }
}
