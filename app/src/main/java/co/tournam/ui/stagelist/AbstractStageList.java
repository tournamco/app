package co.tournam.ui.stagelist;

import android.content.Context;
import android.widget.LinearLayout;

import java.util.List;

import co.tournam.models.TournamentModel;
import co.tournam.models.stage.StageModel;

public abstract class AbstractStageList extends LinearLayout {

    protected List<StageModel> stages;
    protected TournamentModel tournament;

    /**
     * The constructor for AbstractStageList.
     *
     * @param context    the current context
     * @param stages     the List of stagemodels
     * @param tournament the tournamentModel
     */
    public AbstractStageList(Context context, List<StageModel> stages, TournamentModel tournament) {
        super(context);

        this.stages = stages;
        this.tournament = tournament;

        build(context);
    }

    /**
     * Build method provides the option of modifying the layout before building
     * its contents
     *
     * @param context the current context
     */
    private void build(Context context) {
        setOrientation(LinearLayout.VERTICAL);
    }
}
