package co.tournam.ui.stagelist;

import android.content.Context;

import java.util.ArrayList;

import co.tournam.models.TournamentModel;
import co.tournam.models.StageModel;


public class StageList extends AbstractStageList {

    /**
     * The constructor for StageList.
     *
     * @param context    the current context
     * @param stages     the list of stageModels
     * @param tournament the tournamentModel.
     */
    public StageList(Context context, ArrayList<StageModel> stages, TournamentModel tournament) {
        super(context, stages, tournament);

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
        for (StageModel stage : this.stages) {
            StageListItem item = new StageListItem(context, stage, tournament);
            this.addView(item);
        }

    }

}
