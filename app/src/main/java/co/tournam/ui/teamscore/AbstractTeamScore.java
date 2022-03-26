package co.tournam.ui.teamscore;

import android.content.Context;
import android.widget.LinearLayout;
import co.tournam.models.MatchModel;


public abstract class AbstractTeamScore extends LinearLayout {

    protected MatchModel match;

    public AbstractTeamScore(Context context, MatchModel match) {
        super(context);

        this.match = match;

        build(context);
    }

    private void build(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);
    }
}
