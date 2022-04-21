package co.tournam.ui.stagelist;

import android.content.Context;

import java.util.ArrayList;

import co.tournam.models.TournamentModel;
import co.tournam.models.StageModel;


public class StageList extends AbstractStageList {
    public StageList(Context context, ArrayList<StageModel> stages, TournamentModel tournament) {
        super(context, stages, tournament);

        build(context);
    }

    public void build(Context context) {


        buildContents(context);
    }

    public void buildContents(Context context) {
        for (StageModel stage : this.stages) {
            StageListItem item = new StageListItem(context, stage, tournament);
            this.addView(item);
        }

    }

}
