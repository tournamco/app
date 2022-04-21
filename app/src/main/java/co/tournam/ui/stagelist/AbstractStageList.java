package co.tournam.ui.stagelist;

import android.content.Context;
import android.widget.LinearLayout;

import java.util.List;

import co.tournam.models.TournamentModel;
import co.tournam.models.StageModel;

public abstract class AbstractStageList extends LinearLayout {

    protected List<StageModel> stages;
    protected TournamentModel tournament;

    public AbstractStageList(Context context, List<StageModel> stages, TournamentModel tournament) {
        super(context);

        this.stages = stages;
        this.tournament = tournament;

        build(context);
    }

    private void build(Context context) {
        setOrientation(LinearLayout.VERTICAL);
    }
}
